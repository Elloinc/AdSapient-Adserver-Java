/*
 * AdSapient - Open Source Ad Server
 * http://www.sourceforge.net/projects/adsapient
 * http://www.adsapient.com
 *
 * Copyright (C) 2001-06 Vitaly Sazanovich
 * Vitaly.Sazanovich@gmail.com
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Library General Public License  as published by the
 * Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 */
package com.adsapient.adserver.beans;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.*;

import com.adsapient.adserver.beans.PackedBitObj;

public class SWFHeader {
    static Logger logger = Logger.getLogger(SWFHeader.class);
    private String       signature;

    private String       compressionType;

    private int          version;

    private long         size;

    private int          nbits;

    private int          xmax;

    private int          ymax;

    private int          width;

    private int          height;

    private int          frameRate;

    private int          frameCount;

    public static String COMPRESSED   = "compressed";

    public static String UNCOMPRESSED = "uncompressed";


    public SWFHeader( File file )
    {
       try
       {
          parseHeader( file );
       }
       catch ( Exception e )
       {
          e.printStackTrace();
       }
    }


    public SWFHeader( String fileName )
    {
       try
       {
          parseHeader( new File( fileName ) );
       }
       catch ( Exception e )
       {
           logger.error(e.getMessage());
       }
    }


    public void parseHeader( File file ) throws Exception
    {
       FileInputStream fis = null;
       byte[] temp = new byte[( int ) file.length()];
       byte[] swf = null;

       try
       {
          fis = new FileInputStream( file );
          fis.read( temp );
          fis.close();


          if ( !isSWF( temp ) )
          {
             throw new Exception(
                   "File does not appear to be a swf - incorrect file signature" );
          }
          else
          {
             signature = "" + ( char ) temp[0] + ( char ) temp[1]
                   + ( char ) temp[2];
          }

          if ( isCompressed( temp[0] ) )
          {
             swf = decompress( temp );
             compressionType = SWFHeader.COMPRESSED;
          }
          else
          {
             swf = temp;
             compressionType = SWFHeader.UNCOMPRESSED;
          }

       }
       catch ( IOException e )
       {
          System.err.println( e );
       }
       version = swf[3];
       size = readSize( swf );

       nbits = ( ( swf[8] & 0xff ) >> 3 );

       PackedBitObj pbo = readPackedBits( swf, 8, 5, nbits );

       PackedBitObj pbo2 = readPackedBits( swf, pbo.nextByteIndex,
             pbo.nextBitIndex, nbits );

       PackedBitObj pbo3 = readPackedBits( swf, pbo2.nextByteIndex,
             pbo2.nextBitIndex, nbits );

       PackedBitObj pbo4 = readPackedBits( swf, pbo3.nextByteIndex,
             pbo3.nextBitIndex, nbits );

       xmax = pbo2.value;
       ymax = pbo4.value;

       width = convertTwipsToPixels( xmax );
       height = convertTwipsToPixels( ymax );

       int bytePointer = pbo4.nextByteIndex + 2;

       frameRate = swf[bytePointer];
       bytePointer++;

       frameCount = 0;

       for ( int i = 0; i < 2; i++ )
       {
          frameCount = ( frameCount << 8 ) + swf[bytePointer];
          bytePointer++;
       }

       frameCount = ( frameCount >> 8 ) + ( ( frameCount & 0xff ) << 8 );

    }

    public void read( byte[] output, byte[] input, int offset )
    {
       System.arraycopy( input, offset, output, 0, output.length - offset );
    }

    public int readSize( byte[] bytes )
    {
       int size = 0;
       for ( int i = 0; i < 4; i++ )
       {
          size = ( size << 8 ) + bytes[i + 4];
       }

       size = Integer.reverseBytes( size );

       return size;
    }

    public PackedBitObj readPackedBits( byte[] bytes, int byteMarker,
                                        int bitMarker, int length )
    {
       int total = 0;
       int mask = 7 - bitMarker;
       int counter = 0;
       int bitIndex = bitMarker;
       int byteIndex = byteMarker;

       while ( counter < length )
       {

          for ( int i = bitMarker; i < 8; i++ )
          {
             int bit = ( ( bytes[byteMarker] & 0xff ) >> mask ) & 1;
             total = ( total << 1 ) + bit;
             bitIndex = i;
             mask--;
             counter++;
             if ( counter == length )
             {
                break;
             }
          }
          byteIndex = byteMarker;
          byteMarker++;
          bitMarker = 0;
          mask = 7;
       }
       return new PackedBitObj( bitIndex, byteIndex, total );
    }

    public int convertTwipsToPixels( int twips )
    {
       return twips / 20;
    }

    public int convertPixelsToTwips( int pixels )
    {
       return pixels * 20;
    }

    public boolean isSWF( byte[] signature )
    {
       String sig = "" + ( char ) signature[0] + ( char ) signature[1]
             + ( char ) signature[2];

       if ( sig.equals( "FWS" ) || sig.equals( "CWS" ) )
       {
          return true;
       }
       else
       {
          return false;
       }
    }

    public boolean isCompressed( int firstByte )
    {
       if ( firstByte == 67 )
       {
          return true;
       }
       else
       {
          return false;
       }
    }

    public byte[] compress( byte[] bytes, int length )
    {

       byte[] compressed = null;
       byte[] temp = new byte[length];

       Deflater deflater = new Deflater( Deflater.BEST_COMPRESSION );
       deflater.setInput( bytes, 8, length - 8 );
       deflater.finish();

       int compressedLength = deflater.deflate( temp );

       compressed = new byte[compressedLength + 8];

       System.arraycopy( bytes, 0, compressed, 0, 8 );

       System.arraycopy( temp, 0, compressed, 8, compressedLength );

       temp[0] = 67;

       int bl = bytes.length;
       temp[4] = ( byte ) ( bl & 0x000000FF );
       temp[5] = ( byte ) ( ( bl & 0x0000FF00 ) >> 8 );
       temp[6] = ( byte ) ( ( bl & 0x00FF0000 ) >> 16 );
       temp[7] = ( byte ) ( ( bl & 0xFF000000 ) >> 24 );

       return compressed;

    }

    public byte[] decompress( byte[] bytes ) throws DataFormatException
    {
       int size = readSize( bytes );

       byte[] uncompressed = new byte[size];
       System.arraycopy( bytes, 0, uncompressed, 0, 8 );

       Inflater inflater = new Inflater();
       inflater.setInput( bytes, 8, bytes.length - 8 );
       inflater.inflate( uncompressed, 8, size - 8 );
       inflater.finished();
       uncompressed[0] = 70;

       return uncompressed;
    }

    public static void main( String[] args )
    {
       if ( args.length != 1 )
       {
          System.err.println( "usage: swf_file" );
       }
       else
       {
          try
          {
             new SWFHeader( args[0] );
          }
          catch ( Exception e )
          {
             System.err.println( e.getMessage() );
          }
       }

    }

    public int getFrameCount()
    {
       return frameCount;
    }

    public int getFrameRate()
    {
       return frameRate;
    }

    public int getNbits()
    {
       return nbits;
    }

    public String getSignature()
    {
       return signature;
    }

    public long getSize()
    {
       return size;
    }

    public int getVersion()
    {
       return version;
    }

    public int getXmax()
    {
       return xmax;
    }

    public int getYmax()
    {
       return ymax;
    }

    public String getCompressionType()
    {
       return compressionType;
    }

    public int getHeight()
    {
       return height;
    }

    public int getWidth()
    {
       return width;
    }

}
