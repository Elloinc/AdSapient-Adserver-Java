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

public class PackedBitObj {

    public int bitIndex = 0;

    public int byteIndex = 0;

    public int value = 0;

    public int nextBitIndex = 0;

    public int nextByteIndex = 0;

    public int nextByteBoundary = 0;


    public PackedBitObj(int bitMarker, int byteMarker, int decimalValue) {
        bitIndex = bitMarker;
        byteIndex = byteMarker;
        value = decimalValue;
        nextBitIndex = bitMarker;

        if (bitMarker < 7) {
            nextBitIndex++;
            nextByteIndex = byteMarker;
            nextByteBoundary = byteMarker++;
        } else {
            nextBitIndex = 0;
            nextByteIndex++;
            nextByteBoundary = nextByteIndex;
        }
    }
}
