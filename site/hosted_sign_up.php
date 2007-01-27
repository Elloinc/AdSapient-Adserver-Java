<?php include "inc/functions.php" ?>
<?php
	include "mail_config.php";
	$countries = array('Afghanistan','Albania','Algeria','Andorra','Angola','Antiguaand Barbuda','Argentina','Armenia','Australia','Austria','Azerbaijan','The Bahamas','Bahrain','Bangladesh','Barbados','Belarus','Belgium','Belize','Benin','Bhutan','Bolivia','Bosniaand Herzegovina','Botswana','Brazil','Brunei','Bulgaria','Burkina Faso','Burundi','Cambodia','Cameroon','Canada','CapeVerde','Chad','Chile','China','Colombia','Comoros','Congo','Costa Rica','Cote dIvoire','Croatia','Cuba','Cyprus','Czech Republic','Denmark','Djibouti','Dominica','Dominican Republic','Ecuador','Egypt','El Salvador','Equatorial Guinea','Eritrea','Estonia','Ethiopia','Fiji','Finland','France','Gabon','The Gambia','Georgia','Germany','Ghana','Greece','Grenada','Guatemala','Guinea','Guinea-Bissau','Guyana','Haiti','Honduras','Hungary','Iceland','India','Indonesia','Iran','Iraq','Ireland','Israel','Italy','Jamaica','Japan','Jordan','Kazakhstan','Kenya','Kiribati','Korea, North','Korea, South','Kuwait','Kyrgyzstan','Laos','Latvia','Lebanon','Lesotho','Liberia','Libya','Liechtenstein','Lithuania','Luxembourg','Macedonia','Madagascar','Malawi','Malaysia','Maldives','Mali','Malta','Marshall Islands','Mauritania','Mauritius','Mexico','Micronesia','Moldova','Monaco','Mongolia','Morocco','Mozambique','Myanmar (Burma)','Namibia','Nauru','Nepal','Netherlands','New Zealand','Nicaragua','Niger','Nigeria','Norway','Oman','Pakistan','Palau','Panama','Papua New Guinea','Paraguay','Peru','Philippines','Poland','Portugal','Qatar','Romania','Russia','Rwanda','Saint Kitts and Nevis','Saint Lucia','Saint Vincent','Samoa','San Marino','Sao Tome and Principe','Saudi Arabia','Senegal','Serbia and Montenegro','Seychelles','Sierra Leone','Singapore','Slovakia','Slovenia','Solomon Islands','Somalia','South Africa','Spain','Sri Lanka','Sudan','Suriname','Swaziland','Sweden','Switzerland','Syria','Taiwan','Tajikistan','Tanzania','Thailand','Togo','Tonga','Trinidad and Tobago','Tunisia','Turkey','Turkmenistan','Tuvalu','Uganda','Ukraine','United Arab Emirates','United Kingdom','United States','Uruguay','Uzbekistan','Vanuatu','Vatican City(HolySee)','Venezuela','Vietnam','Yemen','Zambia','Zimbabwe');

$volume = array('Less than 10,000','100,000','200,000','300,000','400,000','500,000','600,000','700,000','800,000','900,000','1,000,000','3,000,000','5,000,000','10,000,000','25,000,000','50,000,000','100,000,000','more than 100,000,000');
$hosting = array('don\'t know','shared','dedicated');
	
	if (isset($_REQUEST['request'])) {
	
		if (empty($_REQUEST['firstName'])) {
			$flag_firstName = true;			
			$flag = true;
		}			
		
		if (empty($_REQUEST['lastName'])) {
			$flag_lastName = true;			
			$flag = true;
		}	
		if (empty($_REQUEST['email']) || 
			!preg_match("/^[a-z0-9\\_\\.]+@[a-z0-9\\-]+\\.[a-z]+\\.?[a-z]{2,4}$/i",
						trim($_REQUEST['email']))) {
			$flag_email = true;			
			$flag = true;
		}			
		if (empty($_REQUEST['userName'])) {
			$flag_userName = true;			
			$flag = true;
		}	
		if (empty($_REQUEST['password'])) {
			$flag_password = true;			
			$flag = true;
		}	
		if (empty($_REQUEST['passwordConfirm']) || ($_REQUEST['passwordConfirm']!=$_REQUEST['password'])) {
			$flag_passwordConfirm = true;			
			$flag = true;
		}	
		if ($_REQUEST['country'] == "0") {
			$flag_country = true;			
			$flag = true;
		}	
		if ($_REQUEST['volume'] == "0") {
			$volume_flag = true;			
			$flag = true;
		}	
		if (empty($_REQUEST['address1'])) {
			$flag_address1 = true;			
			$flag = true;
		}
		if (empty($_REQUEST['city'])) {
			$flag_city = true;			
			$flag = true;
		}
		if (empty($_REQUEST['zipCode'])) {
			$flag_zipCode = true;			
			$flag = true;
		}	
		if (empty($_REQUEST['phone'])) {
			$flag_phone = true;			
			$flag = true;
		}	

		
		if($flag) {
			$mesg = "<b style='color: red'>Please, fill in all the required fields!</b> ";
		}
		else {
		
			$header  = "Content-type: text/html; charset=windows-1251 \r\n";
			$header .= "From: {$email_from} <{$email_from}> \r\n";
			if (!empty($email_cc))   $header .= "Cc: {$email_cc} <{$email_cc}> \r\n";
			if (!empty($email_bcc)) $header .= "Bcc: {$email_bcc} <{$email_bcc}> \r\n";
			$body = "<html><head><title>Free Edition Request</title></head>
				<body> <br>
					<div style='margin-left: 100px'>
						Company: {$_REQUEST['company']}<br> 
						First Name: {$_REQUEST['firstName']}<br> 
						Last Name: {$_REQUEST['lastName']}<br> 
						User Name: {$_REQUEST['userName']}<br> 
						Password: {$_REQUEST['password']}<br> 
						Email:{$_REQUEST['email']}<br> 
						Address 1:{$_REQUEST['address1']}<br> 
						Address 2:{$_REQUEST['address2']}<br> 
						City:{$_REQUEST['city']}<br> 
						State:{$_REQUEST['state']}<br> 
						Zip Code:{$_REQUEST['zipCode']}<br> 
						Country:{$_REQUEST['country']}<br> 
						Phone:{$_REQUEST['phone']}<br> 
						Fax:{$_REQUEST['fax']}<br> 
						Site:{$_REQUEST['f13']}<br>
						Monthly Impressions:{$_REQUEST['volume']}<br> 
					</div>
				</body>
				</html>";
			mail($email_to, 'Hosted Service Signup', $body, $header) or die("Email error: contact admin");
			header("Location: hosted_sign_up.php?success=true");	
			exit(0);	
		}
	}	
	else {
		$mesg= "";
		if ($_GET['success']=='true'){
			$mesg = "<b style='color: blue'>Thank you! Your registration has been sent. We will send login information to you shortly.</b>";
		}
	}

?>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<link rel=stylesheet href="adsapient.css" type="text/css">
	<title>AdSapient Ad Serving Solutions - Sign up for a free AdSapient Hosted Service account</title>
	<meta name="description" content="Ad serving solutions: Banner ad management software and ad network software available both a hosted solution and as a standalone program. Smart demographic, psychographic, context and content targeting. ">

	<meta name="keywords" content="ad network software, advertising agency software, ad management, adserver, ad server, ad serve, ad serving, ad delivery, banner software, desktop advertising, desktop advertising software, advertising delivery, banner exchange software, ad banners, ad server, ad server solutions, ad-server, banner ad management, online ad software, ad delivery software, ad management, ad targetting, admanager, ad manager, adserve, advertising software, tracking software, ad tracking software, banner server, banner utility, banner serving, banner management software, ad serving technology, rotating banner ads, affiliate management, affiliate software, banner exchange, affiliate tracking, ad network, ad networks">
<META HTTP-EQUIV="expires" VALUE="Thu, 16 Mar 2000 11:00:00 GMT">
	<meta HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">	
	<script language="javascript" type="text/javascript" src="javascript/menu.js"></script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="preloadImages();">

<center>
<table cellspacing="0" cellpadding="0" width="865" border="0">
<tr><td align="center"><img alt="AdSapient Ad Serving Solutions" src="images/logo.gif"></td>
<td align="right" valign="bottom"><map NAME="bg_Map">
		<area SHAPE="poly" ALT="contact" title="Contact" COORDS="288,20, 392,20, 381,0, 299,0" HREF="contact.php"
			ONMOUSEOVER="changeImages('bg', 'images/bg-contact_over.gif'); return true;"
			ONMOUSEOUT="changeImages('bg', 'images/bg.gif'); return true;">
		<area SHAPE="poly" ALT="support" title="Support" COORDS="207,20, 196,0, 299,0, 288,20" HREF="support.php"
			ONMOUSEOVER="changeImages('bg', 'images/bg-support_over.gif'); return true;"
			ONMOUSEOUT="changeImages('bg', 'images/bg.gif'); return true;">
		<area SHAPE="poly" ALT="products" title="Products" COORDS="93,20, 82,0, 195,0, 184,20" HREF="products.php"
			ONMOUSEOVER="changeImages('bg', 'images/bg-products_over.gif'); return true;"
			ONMOUSEOUT="changeImages('bg', 'images/bg.gif'); return true;">
		<area SHAPE="poly" ALT="home" title="Home" COORDS="1,20, 12,0, 82,0, 93,20" HREF="index.php"
			ONMOUSEOVER="changeImages('bg', 'images/bg-home_over.gif'); return true;"
			ONMOUSEOUT="changeImages('bg', 'images/bg.gif'); return true;">
	</map>
	<img NAME="bg" SRC="images/bg.gif" WIDTH=392 HEIGHT=20 BORDER=0 USEMAP="#bg_Map"></td></tr>
</table>
</center>

<center>
<table width="865"  cellspacing="0" cellpadding="0" >
<tr>
<td width="3"><img src="images/angle1.gif"></td>
<td width="200" height="3"style="background-image: url(images/vert.gif);"></td>
<td><img src="images/angle7.gif"></td>
<td width="656" height="3"style="background-image: url(images/vert.gif);"></td>
<td width="3"><img src="images/angle2.gif"></td>
</tr>

<tr>
<td height="174" width="3" style="background-image: url(images/hor.gif);"></td>
<td width="200" height="174" valign="top" style="background-color: D1DFFF;">
<table border="0" width="100%">
<?php echo(getMenu('','bm')); ?>
<tr><td><hr></td></tr>
<?php echo(getMenu('','an')); ?>
<tr><td><hr></td></tr>
<?php echo(getMenu('','aux')); ?>
</table>
</td>

<td height="174" width="3" style="background-image: url(images/hor.gif);"></td>
<td width="556" height="174" valign="top" style="background-color: #FFE8D0;">
<table border="0" cellspacing="10" width="">
<tr><td class="title2">AdSapient Hosted Service Signup</td></tr>
<tr><td class="promotext">
&nbsp;&nbsp;&nbsp;Please fill out this form to create a free account at AdSapient Hosted Service. 
Note that you can serve up to 10,000 ad views per month for free. 
You can learn the prices for Hosted Services on the <a class="promotext2" href="bm/purchase.php">AdSapient Banner Manager purchase page</a>. 
<br/>
&nbsp;&nbsp;&nbsp;AdSapient Hosted Service (ASP) works like a virtual Banner Manager, where you register sites and ad places, create advertising campaigns, upload banners and display them on your own sites. With that you don't have to worry about software installation, upgrades or hackers. 
</td></tr>

<tr><td class="promotext">
<tr><td colspan="2" class="promotext">&nbsp;&nbsp;&nbsp;<?= $mesg ?></td></tr>

<?php if(!$flag && isset($_REQUEST['request'])) echo "<!--" ;?>
<form action="hosted_sign_up.php" method="POST">
<input name="request" value="1" type="hidden">
<tr><td class="promotext">
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" bgcolor="#0033CC"> 
  <tr>
    <td>
<table width="100%" border="0" cellspacing="1" cellpadding="3">
				<tr>
					<td class="featuresbody">Company</td>
					<td width="250" class="featuresbody"><input class="ffField" name="company" value="<?= $_REQUEST['company']?>" type="text"></td>
				</tr>
				<tr>
					<td class="featuresbody" <?php if($flag_firstName) echo "style='color: red;'"?>>First Name *</td>
					<td class="featuresbody" ><input class="ffField" name="firstName"  value="<?= $_REQUEST['firstName']?>" type="text"></td>
				</tr>
				<tr>
					<td class="featuresbody" <?php if($flag_lastName) echo "style='color: red;'"?>>Last Name *</td>
					<td class="featuresbody" ><input class="ffField"  value="<?= $_REQUEST['lastName']?>" name="lastName" type="text"></td>
				</tr>
				<tr>
					<td class="featuresbody" <?php if($flag_userName) echo "style='color: red;'"?>>User Name *</td>
					<td class="featuresbody" ><input class="ffField2"  value="<?= $_REQUEST['userName']?>" name="userName" type="text"></td>
				</tr>
				<tr>
					<td class="featuresbody" <?php if($flag_password) echo "style='color: red;'"?>>Password *</td>
					<td class="featuresbody" ><input class="ffField2"  value="<?= $_REQUEST['password']?>" name="password" type="password"></td>
				</tr>
				<tr>
					<td class="featuresbody" <?php if($flag_passwordConfirm) echo "style='color: red;'"?>>Password (confirm)  *</td>
					<td class="featuresbody" ><input class="ffField2"  value="<?= $_REQUEST['passwordConfirm']?>" name="passwordConfirm" type="password"></td>
				</tr>
				<tr>
					<td class="featuresbody" <?php if($flag_email) echo "style='color: red;'"?>>Email *<br/><font size="1">(we will send login information to this email)</font></td>
					<td class="featuresbody"><input class="ffField" name="email" value="<?= $_REQUEST['email']?>"  type="text"></td>
				</tr>
				<tr>
					<td class="featuresbody" <?php if($flag_address1) echo "style='color: red;'"?>>Address 1 *</td>
					<td class="featuresbody"><input class="ffField" name="address1"  value="<?= $_REQUEST['address1']?>" type="text"></td>
				</tr>
				<tr>
					<td class="featuresbody">Address 2</td>
					<td class="featuresbody"><input class="ffField" name="address2" value="<?= $_REQUEST['address2']?>"  type="text"></td>
				</tr>
				<tr>
					<td class="featuresbody" <?php if($flag_city) echo "style='color: red;'"?>>City *</td>
					<td class="featuresbody"><input class="ffField2" name="city"  value="<?= $_REQUEST['city']?>" type="text"></td>
				</tr>
				<tr>
					<td class="featuresbody">State</td>
					<td class="featuresbody"><input class="ffField2" name="state"  value="<?= $_REQUEST['state']?>" type="text"></td>
				</tr>
				<tr>
					<td class="featuresbody" <?php if($flag_zipCode) echo "style='color: red;'"?>>ZIP Code *</td>
					<td class="featuresbody"><input class="ffField2" name="zipCode"  value="<?= $_REQUEST['zipCode']?>" type="text"></td>
				</tr>
				<tr>
					<td class="featuresbody" <?php if($flag_country) echo "style='color: red;'"?>>Country *</td>
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
					<td class="featuresbody" <?php if($flag_phone) echo "style='color: red;'"?>>Phone *</td>
					<td class="featuresbody"><input class="ffField2"  value="<?= $_REQUEST['phone']?>"  name="phone" type="text"></td>
				</tr>
				<tr>
					<td class="featuresbody">Fax</td>
					<td class="featuresbody"><input class="ffField2"   value="<?= $_REQUEST['fax']?>" name="fax" type="text"></td>
				</tr>
				<!--
				<tr>
					<td class="featuresbody" <?php if($flag_f13) echo "style='color: red;'"?>>Site(s) *<br/><font size="1">(where banners will appear)</font></td>
					<td class="featuresbody"><input class="ffField"  value="<?= $_REQUEST['f13']?>"  name="f13" type="text"></td>
				</tr>-->
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
<!--
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
-->
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
</table>
</td>
<td height="174" width="3" style="background-image: url(images/hor.gif);"></td>
</tr>
<tr>
<td width="3"><img src="images/angle4.gif"></td>
<td height="3"style="background-image: url(images/vert.gif);"></td>
<td width="3"><img src="images/angle5.gif"></td>
<td height="3"style="background-image: url(images/vert.gif);"></td>
<td width="3"><img src="images/angle3.gif"></td>
</tr>
</table>
</center>

<div align="center"><span class="copyright">© 2004 AdSapient. All Rights Reserved. | </span><a href="terms.php" class="copyright">Terms</a><a href="privacy.php" class="copyright"> | Privacy</a></div>
<br>


</body>
</html>
