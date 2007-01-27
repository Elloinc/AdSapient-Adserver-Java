<?php
	include "mail_config.php";
	if (isset($_REQUEST['request'])) {
		if (empty($_REQUEST['FirstName'])) {
			$flag_fname = true;			
			$flag = true;
		}			
		
		if (empty($_REQUEST['LastName'])) {
			$flag_lname = true;			
			$flag = true;
		}			
		
/*
   This is not required field

   if (empty($_REQUEST['Company'])) {
			$flag_company = true;			
			$flag = true;
		}			
*/		
		if (empty($_REQUEST['Email']) || 
			!preg_match("/^[a-z0-9\\_\\.]+@[a-z0-9\\-]+\\.[a-z]+\\.?[a-z]{2,4}$/i",
						trim($_REQUEST['Email']))) {
			$flag_email = true;			
			$flag = true;
		}			
		
		if (empty($_REQUEST['Inquiry'])) {
			$flag_details = true;			
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
			$body = "<html><head><title>{$subject}</title></head>
				<body> <br>
					<div style='margin-left: 100px'>
						First Name: {$_REQUEST['FirstName']}<br> 
						Last Name: {$_REQUEST['LastName']}<br> 
						Company: {$_REQUEST['Company']}<br> 
						Email:{$_REQUEST['Email']}<br> 
						<b style='color: blue'>Details of project</b><br>
						{$_REQUEST['Inquiry']} 
					</div>
				</body>
				</html>";
			mail($email_to, $subject, $body, $header) or die("Email error: concatact with admin");
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
	<link rel=stylesheet href="adsapient.css" type="text/css">
	<title>AdSapient Ad Serving Solutions - Contact</title>
	<meta name="description" content="Ad serving solutions: Banner ad management software and ad network software available both a hosted solution and as a standalone program. Smart demographic, psychographic, context and content targeting. ">
	<meta name="keywords" content="ad network software, advertising agency software, ad management, adserver, ad server, ad serve, ad serving, ad delivery, banner software, desktop advertising, desktop advertising software, advertising delivery, banner exchange software, ad banners, ad server, ad server solutions, ad-server, banner ad management, online ad software, ad delivery software, ad management, ad targetting, admanager, ad manager, adserve, advertising software, tracking software, ad tracking software, banner server, banner utility, banner serving, banner management software, ad serving technology, rotating banner ads, affiliate management, affiliate software, banner exchange, affiliate tracking, ad network, ad networks">
	<meta HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
	<script language="javascript" type="text/javascript" src="javascript/menu.js"></script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="preloadImages();">
<center>
<table cellspacing="0" cellpadding="0" width="865" border="0">
<tr><td align="center"><img alt="AdSapient Ad Serving Solutions" src="images/logo.gif"></td>
<td align="right" valign="bottom"><map NAME="bg_Map">
		<area SHAPE="poly" ALT="" COORDS="288,20, 392,20, 381,0, 299,0" HREF="contact.php"
			ONMOUSEOVER="changeImages('bg', 'images/bg-contact_over.gif'); return true;"
			ONMOUSEOUT="changeImages('bg', 'images/bg.gif'); return true;">
		<area SHAPE="poly" ALT="" COORDS="207,20, 196,0, 299,0, 288,20" HREF="support.php"
			ONMOUSEOVER="changeImages('bg', 'images/bg-support_over.gif'); return true;"
			ONMOUSEOUT="changeImages('bg', 'images/bg.gif'); return true;">
		<area SHAPE="poly" ALT="" COORDS="93,20, 82,0, 195,0, 184,20" HREF="products.php"
			ONMOUSEOVER="changeImages('bg', 'images/bg-products_over.gif'); return true;"
			ONMOUSEOUT="changeImages('bg', 'images/bg.gif'); return true;">
		<area SHAPE="poly" ALT="" COORDS="1,20, 12,0, 82,0, 93,20" HREF="index.php"
			ONMOUSEOVER="changeImages('bg', 'images/bg-home_over.gif'); return true;"
			ONMOUSEOUT="changeImages('bg', 'images/bg.gif'); return true;">
	</map>
	<img NAME="bg" SRC="images/bg.gif" WIDTH=392 HEIGHT=20 BORDER=0 USEMAP="#bg_Map"></td></tr>
</table>
</center>

<center>
<table cellspacing="0" cellpadding="0" >
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
<br><br>
<tr><td><nobr><a class="promotext2" href="about.php">&nbsp;&nbsp;&nbsp;About AdSapient Inc.</a></nobr></td></tr>
<tr><td><nobr><a class="promotext2" href="contact.php">&nbsp;&nbsp;&nbsp;Contact us</a></nobr></td></tr>
<tr><td align="center">
<table cellpadding="0" cellspacing="0" border="0">
<tr><br><td align="center"><!--<a href="http://chat.boldcenter.com/aid/1383567893338894994/bc.chat" target="_blank" onClick="this.newWindow = window.open('http://chat.boldcenter.com/aid/1383567893338894994/bc.chat?url=' + document.location, 'Chat', 'toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=1,width=640,height=480');this.newWindow.focus();this.newWindow.opener=window;return false;"><img alt="Live Customer Support" src="images/live.gif" width="120" height="70" border="0"></a>--></td></tr>
</table>
</td></tr>
</table>
</td>

<td height="174" width="3" style="background-image: url(images/hor.gif);"></td>
<td width="656" height="174" valign="top" style="background-color: #FFE8D0;">
<center>
<table border="0" cellspacing="10">
<tr><td colspan="2" class="title2">Contact</td></tr>
<tr><td colspan="2" class="promotext">Contact us at <a class="promotext2" href="mailto:contact@adsapient.com">contact@adsapient.com</a> or using this form: </td></tr>









<tr><td colspan="2" class="promotext">&nbsp;&nbsp;&nbsp; <?= $mesg ?></td></tr>
<?php if(!$flag && isset($_REQUEST['request'])) echo "<!--" ;?>
<form action="contact.php" method="POST">
<input name="request" value="1" type="hidden">
<tr>
<td class="promotext"<?php if($flag_fname) echo "style='color: red;'"?>>First Name<span class="ffRequiredFlag">*</span></td>
<td width="250" class="promotext"><font face="Verdana,Arial,helvetica" size="1">
<input class="ffField" name="FirstName" type="text" value="<?= $_REQUEST['FirstName']?>" size="22" maxlength="50"></font>
</td>
</tr>
<tr>
<td class="promotext" <?php if($flag_lname) echo "style='color: red;'"?>>Last Name<span class="ffRequiredFlag">*</span></td>
<td class="promotext"><font face="Verdana,Arial,helvetica" size="1"><input class="ffField" name="LastName" type="text" value="<?= $_REQUEST['LastName']?>" size="22" maxlength="50"></font>
</td>
</tr>
<tr>
<td class="promotext">Company</td>
<td class="promotext"><font face="Verdana,Arial,helvetica" size="1"><input class="ffField" name="Company" type="text" value="<?= $_REQUEST['Company']?>" size="30" maxlength="50"></font>
</td>
</tr>
<tr>
<td class="promotext"<?php if($flag_email) echo "style='color: red;'"?>>Email<span class="ffRequiredFlag">*</span><?php if($flag_email) echo " not valid"?></td>
<td class="promotext"><font face="Verdana,Arial,helvetica" size="1"><input class="ffField" name="Email" type="text" value="<?= $_REQUEST['Email']?>" size="30" maxlength="200"></font>
</td>
</tr>
<tr><td valign="top" class="promotext" <?php if($flag_details) echo "style='color: red;'"?>>Comments or Questions<span class="ffRequiredFlag">*</span></td>
<td><font face="Verdana,Arial,helvetica" size="1">
<textarea class="ffField" name="Inquiry"  cols="41" rows="8" wrap="virtual"><?= $_REQUEST['Inquiry']?></textarea></font></td>
</tr>
<tr><td></td><td><input type="submit" value="Submit"></td></tr>
<tr><td></td></tr>
</form>
<?php if(!$flag && isset($_REQUEST['request'])) echo "-->" ;?>
</table>
</center>
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

<div align="center"><span class="copyright">© 2004 AdSapient. All Rights Reserved. | </span><a href="terms.php" class="copyright">Terms</a></span><a href="privacy.php" class="copyright"> | Privacy</a></div>
<br>


</body>
</html>

