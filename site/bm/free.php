<?php include "../inc/functions.php" ?>
<?php
	include "../mail_config.php";
	$countries = array('Afghanistan','Albania','Algeria','Andorra','Angola','Antiguaand Barbuda','Argentina','Armenia','Australia','Austria','Azerbaijan','The Bahamas','Bahrain','Bangladesh','Barbados','Belarus','Belgium','Belize','Benin','Bhutan','Bolivia','Bosniaand Herzegovina','Botswana','Brazil','Brunei','Bulgaria','Burkina Faso','Burundi','Cambodia','Cameroon','Canada','CapeVerde','Chad','Chile','China','Colombia','Comoros','Congo','Costa Rica','Cote dIvoire','Croatia','Cuba','Cyprus','Czech Republic','Denmark','Djibouti','Dominica','Dominican Republic','Ecuador','Egypt','El Salvador','Equatorial Guinea','Eritrea','Estonia','Ethiopia','Fiji','Finland','France','Gabon','The Gambia','Georgia','Germany','Ghana','Greece','Grenada','Guatemala','Guinea','Guinea-Bissau','Guyana','Haiti','Honduras','Hungary','Iceland','India','Indonesia','Iran','Iraq','Ireland','Israel','Italy','Jamaica','Japan','Jordan','Kazakhstan','Kenya','Kiribati','Korea, North','Korea, South','Kuwait','Kyrgyzstan','Laos','Latvia','Lebanon','Lesotho','Liberia','Libya','Liechtenstein','Lithuania','Luxembourg','Macedonia','Madagascar','Malawi','Malaysia','Maldives','Mali','Malta','Marshall Islands','Mauritania','Mauritius','Mexico','Micronesia','Moldova','Monaco','Mongolia','Morocco','Mozambique','Myanmar (Burma)','Namibia','Nauru','Nepal','Netherlands','New Zealand','Nicaragua','Niger','Nigeria','Norway','Oman','Pakistan','Palau','Panama','Papua New Guinea','Paraguay','Peru','Philippines','Poland','Portugal','Qatar','Romania','Russia','Rwanda','Saint Kitts and Nevis','Saint Lucia','Saint Vincent','Samoa','San Marino','Sao Tome and Principe','Saudi Arabia','Senegal','Serbia and Montenegro','Seychelles','Sierra Leone','Singapore','Slovakia','Slovenia','Solomon Islands','Somalia','South Africa','Spain','Sri Lanka','Sudan','Suriname','Swaziland','Sweden','Switzerland','Syria','Taiwan','Tajikistan','Tanzania','Thailand','Togo','Tonga','Trinidad and Tobago','Tunisia','Turkey','Turkmenistan','Tuvalu','Uganda','Ukraine','United Arab Emirates','United Kingdom','United States','Uruguay','Uzbekistan','Vanuatu','Vatican City(HolySee)','Venezuela','Vietnam','Yemen','Zambia','Zimbabwe');

$volume = array('Less than 10,000','100,000','200,000','300,000','400,000','500,000','600,000','700,000','800,000','900,000','more than 1,000,000');
$hosting = array('don\'t know','shared','dedicated');

	
	if (isset($_REQUEST['request'])) {
	
		if (empty($_REQUEST['f2'])) {
			$flag_f2 = true;			
			$flag = true;
		}			
		
		if (empty($_REQUEST['f3'])) {
			$flag_f3 = true;			
			$flag = true;
		}	
		if (empty($_REQUEST['f4']) || 
			!preg_match("/^[a-z0-9\\_\\.]+@[a-z0-9\\-]+\\.[a-z]+\\.?[a-z]{2,4}$/i",
						trim($_REQUEST['f4']))) {
			$flag_f4 = true;			
			$flag = true;
		}			
		if (empty($_REQUEST['f5'])) {
			$flag_f5 = true;			
			$flag = true;
		}	
		if (empty($_REQUEST['f7'])) {
			$flag_f7 = true;			
			$flag = true;
		}	
		if (empty($_REQUEST['f9'])) {
			$flag_f9 = true;			
			$flag = true;
		}	
		if ($_REQUEST['country'] == "0") {
			$country_flag = true;			
			$flag = true;
		}	
		if ($_REQUEST['volume'] == "0") {
			$volume_flag = true;			
			$flag = true;
		}	
		if ($_REQUEST['hosting'] == "0") {
			$hosting_flag = true;			
			$flag = true;
		}	
		if (empty($_REQUEST['f11'])) {
			$flag_f11 = true;			
			$flag = true;
		}
		if (empty($_REQUEST['f13'])) {
			$flag_f13 = true;			
			$flag = true;
		}	

		
		if($flag) {
			$mesg = "<b style='color: red'>Please, fill in all the fields!</b> ";
		}
		else {
			$header  = "Content-type: text/html; charset=windows-1251 \r\n";
			$header .= "From: {$email_from} <{$email_from}> \r\n";
			if (!empty($email_cc))   $header .= "Cc: {$email_cc} <{$email_cc}> \r\n";
			if (!empty($email_bcc)) $header .= "Bcc: {$email_bcc} <{$email_bcc}> \r\n";
			$body = "<html><head><title>Free Edition Request</title></head>
				<body> <br>
					<div style='margin-left: 100px'>
						Company: {$_REQUEST['f1']}<br> 
						First Name: {$_REQUEST['f2']}<br> 
						Last Name: {$_REQUEST['f3']}<br> 
						Email:{$_REQUEST['f4']}<br> 
						Address 1:{$_REQUEST['f5']}<br> 
						Address 2:{$_REQUEST['f6']}<br> 
						City:{$_REQUEST['f7']}<br> 
						State:{$_REQUEST['f8']}<br> 
						Zip Code:{$_REQUEST['f9']}<br> 
						Country:{$_REQUEST['country']}<br> 
						Phone:{$_REQUEST['f11']}<br> 
						Fax:{$_REQUEST['f12']}<br> 
						Site:{$_REQUEST['f13']}<br>
						Monthly Impressions:{$_REQUEST['volume']}<br> 
						Hosting:{$_REQUEST['hosting']}<br> 
						Hosting Company Web Site:{$_REQUEST['f16']}<br> 
						How did you find us:{$_REQUEST['f17']}<br> 
					</div>
				</body>
				</html>";
			mail($email_to, 'Request for a free edition', $body, $header) or die("Email error: contact admin");
			header("Location: http://www.adsapient.com");	
			exit(0);	
		}
	}	
	else {
		$mesg= "";
	}

?>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<link rel=stylesheet href="../adsapient.css" type="text/css">
	<title>AdSapient Ad Serving Solutions - Free Ad Server Software For Your Web Site</title>
	<meta name="description" content="Ad serving solutions: Banner ad management software and ad network software available both a hosted solution and as a standalone program. Smart demographic, psychographic, context and content targeting. ">

	<meta name="keywords" content="ad network software, advertising agency software, ad management, adserver, ad server, ad serve, ad serving, ad delivery, banner software, desktop advertising, desktop advertising software, advertising delivery, banner exchange software, ad banners, ad server, ad server solutions, ad-server, banner ad management, online ad software, ad delivery software, ad management, ad targetting, admanager, ad manager, adserve, advertising software, tracking software, ad tracking software, banner server, banner utility, banner serving, banner management software, ad serving technology, rotating banner ads, affiliate management, affiliate software, banner exchange, affiliate tracking, ad network, ad networks">
<META HTTP-EQUIV="expires" VALUE="Thu, 16 Mar 2000 11:00:00 GMT">
	<meta HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">	
	<script language="javascript" type="text/javascript" src="../javascript/menu.js"></script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="preloadImages();">

<center>
<table cellspacing="0" cellpadding="0" width="865" border="0">
<tr><td align="center"><img alt="AdSapient Ad Serving Solutions" src="../images/logo.gif"></td>
<td align="right" valign="bottom"><map NAME="bg_Map">
		<area SHAPE="poly" ALT="contact" title="Contact" COORDS="288,20, 392,20, 381,0, 299,0" HREF="../contact.php"
			ONMOUSEOVER="changeImages('bg', '../images/bg-contact_over.gif'); return true;"
			ONMOUSEOUT="changeImages('bg', '../images/bg.gif'); return true;">
		<area SHAPE="poly" ALT="support" title="Support" COORDS="207,20, 196,0, 299,0, 288,20" HREF="../support.php"
			ONMOUSEOVER="changeImages('bg', '../images/bg-support_over.gif'); return true;"
			ONMOUSEOUT="changeImages('bg', '../images/bg.gif'); return true;">
		<area SHAPE="poly" ALT="products" title="Products" COORDS="93,20, 82,0, 195,0, 184,20" HREF="../products.php"
			ONMOUSEOVER="changeImages('bg', '../images/bg-products_over.gif'); return true;"
			ONMOUSEOUT="changeImages('bg', '../images/bg.gif'); return true;">
		<area SHAPE="poly" ALT="home" title="Home" COORDS="1,20, 12,0, 82,0, 93,20" HREF="../index.php"
			ONMOUSEOVER="changeImages('bg', '../images/bg-home_over.gif'); return true;"
			ONMOUSEOUT="changeImages('bg', '../images/bg.gif'); return true;">
	</map>
	<img NAME="bg" SRC="../images/bg.gif" WIDTH=392 HEIGHT=20 BORDER=0 USEMAP="#bg_Map"></td></tr>
</table>
</center>

<center>
<table width="865"  cellspacing="0" cellpadding="0" >
<tr>
<td width="3"><img src="../images/angle1.gif"></td>
<td width="200" height="3"style="background-image: url(../images/vert.gif);"></td>
<td><img src="../images/angle7.gif"></td>
<td width="656" height="3"style="background-image: url(../images/vert.gif);"></td>
<td width="3"><img src="../images/angle2.gif"></td>
</tr>

<tr>
<td height="174" width="3" style="background-image: url(../images/hor.gif);"></td>
<td width="200" height="174" valign="top" style="background-color: D1DFFF;">
<table border="0" width="100%">
<?php echo(getMenu('../','bm')); ?>
</table>
</td>

<td height="174" width="3" style="background-image: url(../images/hor.gif);"></td>
<td width="556" height="174" valign="top" style="background-color: #FFE8D0;">
<table border="0" cellspacing="10" width="">
<!--
<tr><td class="title2">Special Free Edition</td></tr>
<tr><td class="promotext">&nbsp;&nbsp;&nbsp;AdSapient Banner Manager is absolutely free for sites with traffic volume <b>less than 1 million banner impressions per month</b>. There are some other limitations mentioned in the Terms of Usage but the functionality is present to its full extent.</td></tr>
<tr><td class="promotext"><b>Offer Overview</b></td></tr>
<tr><td class="promotext">
<li>you can use the software freely as long as you wish for your own campaigns as well as for commercial ones as long as such usage complies with the below given terms;</li>
<li>banner manager has no site, campaign or banner limitations; you can register as many sites as you wish;</li>
<li>enjoy the full set of targeting, reporting and ad tracking <a class="promotext2" href="features.php">features</a>;</li>
<li>use our <a class="promotext2" href="http://adsapient.com/wiki/index.php" target="_blank">knowledge database</a> for free to find the answers to all of your installation, setup and usage questions;</li>
<li>although support is not included in this offer we hereby pledge our commitment to helping all of our users.</li>
</td></tr>

<tr><td class="promotext"><b>Terms of Usage and Limitations</b></td></tr>
<tr><td class="promotext">

<li>no more than 1 million impressions per month may be served with the free edition;</li>
<li>each user of the free edition should be registered;</li>
<li>no tampering with the code or design is allowed. In case AdSapient, Inc. finds out any of such misuse, it has the right to reclaim the software license and stop its service for the user in question;</li>
<li>each registered user can install the software only on one JBoss instance;</li>
<li>being banner manager the software as such is limited to only one publisher account;</li>

</td></tr>

<tr><td class="promotext"><b>Upgrade Options</b></td></tr>
<tr><td class="promotext">
<li>upgrade to <a class="promotext2" href="prices.php">any commercial license</a> you find appropriate;</li>
<li>no reinstallation is required;</li>
<li>you can upgrade to additional publisher accounts. <a class="promotext2" href="../contact.php">Contact us</a> for details.</li>
</td></tr>

<tr><td class="promotext"><b>How to Get Started</b></td></tr>
<tr><td class="promotext">First off, fill in the following form to register with us. We will send you download link and installation information as soon as we have approved your information.</td></tr>
<tr><td class="promotext">
<tr><td colspan="2" class="promotext">&nbsp;&nbsp;&nbsp;<?= $mesg ?></td></tr>

<?php if(!$flag && isset($_REQUEST['request'])) echo "<!--" ;?>
<form action="free.php" method="POST">
<input name="request" value="1" type="hidden">
<tr><td class="promotext">
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" bgcolor="#0033CC"> 
  <tr>
    <td>
<table width="100%" border="0" cellspacing="1" cellpadding="3">
				<tr>
					<td class="featuresbody">Company</td>
					<td width="250" class="featuresbody"><input class="ffField" name="f1" value="<?= $_REQUEST['f1']?>" type="text"></td>
				</tr>
				<tr>
					<td class="featuresbody" <?php if($flag_f2) echo "style='color: red;'"?>>First Name *</td>
					<td class="featuresbody" ><input class="ffField" name="f2"  value="<?= $_REQUEST['f2']?>" type="text"></td>
				</tr>
				<tr>
					<td class="featuresbody" <?php if($flag_f3) echo "style='color: red;'"?>>Last Name *</td>
					<td class="featuresbody" ><input class="ffField"  value="<?= $_REQUEST['f3']?>" name="f3" type="text"></td>
				</tr>
				<tr>
					<td class="featuresbody" <?php if($flag_f4) echo "style='color: red;'"?>>Email *<br/><font size="1">(we will send download information to this email)</font></td>
					<td class="featuresbody"><input class="ffField" name="f4" value="<?= $_REQUEST['f4']?>"  type="text"></td>
				</tr>
				<tr>
					<td class="featuresbody" <?php if($flag_f5) echo "style='color: red;'"?>>Address 1 *</td>
					<td class="featuresbody"><input class="ffField" name="f5"  value="<?= $_REQUEST['f5']?>" type="text"></td>
				</tr>
				<tr>
					<td class="featuresbody">Address 2</td>
					<td class="featuresbody"><input class="ffField" name="f6" value="<?= $_REQUEST['f6']?>"  type="text"></td>
				</tr>
				<tr>
					<td class="featuresbody" <?php if($flag_f7) echo "style='color: red;'"?>>City *</td>
					<td class="featuresbody"><input class="ffField2" name="f7"  value="<?= $_REQUEST['f7']?>" type="text"></td>
				</tr>
				<tr>
					<td class="featuresbody">State</td>
					<td class="featuresbody"><input class="ffField2" name="f8"  value="<?= $_REQUEST['f8']?>" type="text"></td>
				</tr>
				<tr>
					<td class="featuresbody" <?php if($flag_f9) echo "style='color: red;'"?>>ZIP Code *</td>
					<td class="featuresbody"><input class="ffField2" name="f9"  value="<?= $_REQUEST['f9']?>" type="text"></td>
				</tr>
				<tr>
					<td class="featuresbody" <?php if($country_flag) echo "style='color: red;'"?>>Country *</td>
					<td class="featuresbody">
							<SELECT  class="ffField2" name="country">
								<OPTION value="0">--SELECT--</OPTION>
								<?php
										foreach($countries as $a) {
											if($_REQUEST['country'] == $a)
												echo "<OPTION value='$a' selected>$a</OPTION>";
											else	
												echo "<OPTION value='$a'>$a</OPTION>";
										}
								?>
							</SELECT>	
					</td>
				</tr>
				<tr>
					<td class="featuresbody" <?php if($flag_f11) echo "style='color: red;'"?>>Phone *</td>
					<td class="featuresbody"><input class="ffField2"  value="<?= $_REQUEST['f11']?>"  name="f11" type="text"></td>
				</tr>
				<tr>
					<td class="featuresbody">Fax</td>
					<td class="featuresbody"><input class="ffField2"   value="<?= $_REQUEST['f12']?>" name="f12" type="text"></td>
				</tr>
				<tr>
					<td class="featuresbody" <?php if($flag_f13) echo "style='color: red;'"?>>Site(s) *<br/><font size="1">(where banners will appear)</font></td>
					<td class="featuresbody"><input class="ffField"  value="<?= $_REQUEST['f13']?>"  name="f13" type="text"></td>
				</tr>
				<tr>
					<td class="featuresbody" <?php if($volume_flag) echo "style='color: red;'"?>>Monthly Impressions *</td>
					<td class="featuresbody">
							<SELECT class="ffField2"  name="volume">
								<OPTION value="0">--SELECT--</OPTION>
								<?php
										foreach($volume as $a) {
											if($_REQUEST['volume'] == $a)
												echo "<OPTION value='$a' selected>$a</OPTION>";
											else	
												echo "<OPTION value='$a'>$a</OPTION>";
										}
								?>
							</SELECT>	
</td></tr>
				<tr>
					<td class="featuresbody" <?php if($hosting_flag) echo "style='color: red;'"?>>Hosting *</td>
					<td class="featuresbody">
							<SELECT  class="ffField2" name="hosting">
								<OPTION value="0">--SELECT--</OPTION>
								<?php
										foreach($hosting as $a) {
											if($_REQUEST['hosting'] == $a)
												echo "<OPTION value='$a' selected>$a</OPTION>";
											else	
												echo "<OPTION value='$a'>$a</OPTION>";
										}
								?>
							</SELECT>	
</td></tr>
				<tr>
					<td class="featuresbody">Hosting Company Web Site</td>
					<td class="featuresbody"><input   value="<?= $_REQUEST['f16']?>" class="ffField" name="f16" type="text"></td>
				</tr>
				<tr>
					<td class="featuresbody">How did you find us?</td>
					<td class="featuresbody"><input   value="<?= $_REQUEST['f17']?>" class="ffField" name="f17" type="text"></td>
				</tr>
				<tr>
					<td colspan="2" align="center" class="featuresbody"><center><input  type="submit" value="Submit"></center></td>
				</tr>
</form>
<?php if(!$flag && isset($_REQUEST['request'])) echo "-->" ;?>
</table>
</td>
</tr>
</table>
</td></tr>
-->
</table>
</td>
<td height="174" width="3" style="background-image: url(../images/hor.gif);"></td>
</tr>
<tr>
<td width="3"><img src="../images/angle4.gif"></td>
<td height="3"style="background-image: url(../images/vert.gif);"></td>
<td width="3"><img src="../images/angle5.gif"></td>
<td height="3"style="background-image: url(../images/vert.gif);"></td>
<td width="3"><img src="../images/angle3.gif"></td>
</tr>
</table>
</center>

<div align="center"><span class="copyright">© 2004 AdSapient. All Rights Reserved. | </span><a href="../terms.php" class="copyright">Terms</a><a href="../privacy.php" class="copyright"> | Privacy</a></div>
<br>


</body>
</html>
