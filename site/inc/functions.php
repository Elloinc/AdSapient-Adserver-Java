<?php 
function getMenu($prefix, $menutype)
{
	$return_str = '';
	if ($menutype=='an'){
		$return_str .= '<tr><td align="center"><img border="0" alt="AdSapient Ad Network" src="'.$prefix.'images/logo_adnetwork.gif"></td>';
		$return_str .= '<tr><td><nobr><a class="promotext2" href="'.$prefix.'an/index.php">&nbsp;&nbsp;&nbsp;About AdSapient AN</a></nobr></td></tr>';
		$return_str .= '<tr><td><nobr><a class="promotext2" href="'.$prefix.'an/features.php">&nbsp;&nbsp;&nbsp;Features</a></nobr></td></tr>';
		$return_str .= '<tr><td><nobr><a class="promotext2" href="'.$prefix.'an/screenshots.php">&nbsp;&nbsp;&nbsp;Screenshots</a></nobr></td></tr>';
		//$return_str .= '<tr><td><nobr><a class="promotext2" href="'.$prefix.'an/hosted.php">&nbsp;&nbsp;&nbsp;Managed Solution</a></nobr></td></tr>';
		//$return_str .= '<tr><td><nobr><a class="promotext2" href="'.$prefix.'an/demo.php">&nbsp;&nbsp;&nbsp;Demo</a></nobr></td></tr>';
		$return_str .= '<tr><td><nobr><a class="promotext2" href="'.$prefix.'an/install.php">&nbsp;&nbsp;&nbsp;Sys.Req. and Installation</a></nobr></td></tr>';
		//$return_str .= '<tr><td><nobr><a class="promotext2" href="'.$prefix.'an/purchase.php">&nbsp;&nbsp;&nbsp;Purchase</a></nobr></td></tr>';
	}else if ($menutype=='bm'){
		$return_str .= '<tr><td align="center"><img border="0" alt="AdSapient Banner Manager" src="'.$prefix.'images/logo_bannermanager.gif"></td>';
		$return_str .= "<tr><td><nobr><a class=\"promotext2\" href=\"".$prefix."bm/index.php\">&nbsp;&nbsp;&nbsp;About AdSapient BM</a></nobr></td></tr>";
		$return_str .= "<tr><td><nobr><a class=\"promotext2\" href=\"".$prefix."bm/features.php\">&nbsp;&nbsp;&nbsp;Features</a></nobr></td></tr>";
		$return_str .= "<tr><td><nobr><a class=\"promotext2\" href=\"".$prefix."bm/screenshots.php\">&nbsp;&nbsp;&nbsp;Screenshots</a></nobr></td></tr>";
		//$return_str .= "<tr><td><nobr><a class=\"promotext2\" href=\"".$prefix."bm/hosted.php\">&nbsp;&nbsp;&nbsp;Hosted Service</a></nobr></td></tr>";
		//$return_str .= "<tr><td><nobr><a class=\"promotext2\" href=\"".$prefix."bm/demo.php\">&nbsp;&nbsp;&nbsp;Demo</a></nobr></td></tr>";
		$return_str .= "<tr><td><nobr><a class=\"promotext2\" href=\"".$prefix."bm/install.php\">&nbsp;&nbsp;&nbsp;Sys.Req. and Installation</a></nobr></td></tr>";
		//$return_str .= "<tr><td><nobr><a class=\"promotext2\" href=\"".$prefix."bm/purchase.php\">&nbsp;&nbsp;&nbsp;Purchase</a></nobr></td></tr>";
		//$return_str .= "<tr><td><nobr><a class=\"promotext2\" href=\"".$prefix."bm/free.php\">&nbsp;&nbsp;&nbsp;Special Free Edition</a></nobr></td></tr>";
	}else if ($menutype='aux'){
		$return_str .= '<tr><td><nobr><a class="promotext2" href="'.$prefix.'products.php">&nbsp;&nbsp;&nbsp;Products Overview</a></nobr></td></tr>';
		$return_str .= '<tr><td><nobr><a class="promotext2" href="'.$prefix.'faq.php">&nbsp;&nbsp;&nbsp;FAQ</a></nobr></td></tr>';
		$return_str .= '<tr><td><nobr><a class="promotext2" href="'.$prefix.'behavioral_targeting.php">&nbsp;&nbsp;&nbsp;Behavioral Targeting</a></nobr></td></tr>';
		//$return_str .= '<tr><td><nobr><a class="promotext2" href="'.$prefix.'hosted_sign_up.php">&nbsp;&nbsp;&nbsp;Hosted Service Sign-up</a></nobr></td></tr>';
	}
	return $return_str;
}
?>
