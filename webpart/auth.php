<?php
define('MZLwp',true);
include ("connect.php");
include ("status.php");

$action		= mysql_real_escape_string($_POST['a']);
$login		= mysql_real_escape_string(trim(htmlspecialchars(stripslashes($_POST['user']))));
$pass		= mysql_real_escape_string(trim($_POST['password']));
$seckey		= mysql_real_escape_string($_POST['opt']);
$eml		= mysql_real_escape_string(trim($_POST['mail']));
$usrhost	= mysql_real_escape_string($_POST['localhost']);
//$ver		= mysql_real_escape_string(intval($_POST['version']));
$mip		= trim($_POST['ip']);
$mport		= trim($_POST['port']);

if ((preg_match('/Minecraft ZeTRiX\'s Launcher/i', $_SERVER['HTTP_USER_AGENT']))) {

if (($action == 'reg') && ($login !== null) && ($pass !== null) && ($seckey !== null)) {
	$mzreg = mysql_query("SELECT COUNT($db_username) FROM $db_table WHERE $db_seckey='$seckey'") or die ("Запрос к базе завершился ошибкой.");
	$temparr = mysql_fetch_array($mzreg);
	$mzrow = $temparr[0];
		if ($mzrow == 0) {
			$testusrname = mysql_query("SELECT COUNT($db_id) FROM `$db_table` WHERE $db_username='$login'") or die ("Запрос к базе завершился ошибкой.");
			$rowusrname = mysql_fetch_array($testusrname);
			$usrrow = $rowusrname[0];
			if ($usrrow == 0) {
				mysql_query("INSERT INTO `$db_table` ($db_username, $db_password, $db_seckey, $db_mail, $db_usrhost) VALUES ('$login', '$pass', '$seckey', '$eml', '$usrhost')");
				echo 'Success';
			} else {
				die ('Fail2');
			}
		} else {
			die ('Fail1');
		}
}

if (($action == 'auth') && ($login !== null) && ($pass !== null) && ($seckey !== null)) {
	$result = mysql_query("SELECT $db_password FROM `$db_table` WHERE $db_username='$login'") or die ("Запрос к базе завершился ошибкой.");
    $row = mysql_fetch_array($result);
	$query = mysql_query("UPDATE $db_table SET $db_seckey='$seckey', $db_usrhost='$usrhost' WHERE $db_username='$login'") or die ("Запрос к базе завершился ошибкой.");

	$hash = $row[$db_password];
	$checksumm = sha1(file_get_contents("minecraft.jar"));
	$filesize = filesize("minecraft.jar");
	$session = intval($seckey).rand(9999, 999999);

		if ($hash == $pass) {
			//echo $hash.'|16030|'.$checksumm.'_'.$login.'|17003|'.rand(999, 9999999);
			echo $hash.'<>'.$checksumm.'<>'.$filesize.'<>'.'_'.$login.'<>'.$session;
		} else {
			echo 'Bad Login';
		}
}

if (($action == 'addr') && ($seckey !== null)) {
	echo $masteraddr;
}

if (($action == 'monitor') && ($mip !== null)) {
	$Server = new status($mip, $mport);
	$players = $Server->CurPlayers.':'.$Server->MaxPlayers;
	$offtext = 'OFFLINE';
	$result = $Server->Online ? $players : $offtext;
	echo $result;
}

} else {
	die ('Fuck Off');
}
?>