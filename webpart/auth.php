<?php
define('MZL',true);
include ("connect.php");

$login=$_POST['user'];
$login = trim($login);
$login = htmlspecialchars($login);
$login = mysql_real_escape_string($login);
$login = stripslashes($login);
$pass=$_POST['password'];
//$ver=mysql_real_escape_string(intval($_POST['version']));
$seckey=mysql_real_escape_string($_POST['opt']);
$eml=mysql_real_escape_string(trim($_POST['mail']));

$action=mysql_real_escape_string($_POST['a']);

if(preg_match('/Minecraft ZeTRiX\'s Launcher/i',$_SERVER['HTTP_USER_AGENT'])) {

if ($action == 'reg') {
	$mzreg = mysql_query("SELECT COUNT($db_username) FROM $db_table WHERE $db_seckey='$seckey'") or die ("Запрос к базе завершился ошибкой.");
	$temparr = mysql_fetch_array($mzreg);
	$mzrow = $temparr[0];
		if ($mzrow == 0) {
			mysql_query("INSERT INTO `$db_table` ($db_username, $db_password, $db_seckey, $db_mail) VALUES ('$login', '$pass', '$seckey', '$eml')");
			echo 'Success';
		} else {
			echo 'Fail';
		}
}

if ($action == 'auth') {
	$result = mysql_query("SELECT $db_password FROM `$db_table` WHERE $db_username='$login'") or die ("Запрос к базе завершился ошибкой.");
    $row = mysql_fetch_array($result);

	$hash = $row[$db_password];
	$checksumm = sha1(file_get_contents("minecraft.jar"));
	$filesize = filesize("minecraft.jar");
			
	$query = mysql_query("UPDATE $db_table SET $db_seckey='$seckey' WHERE $db_username='$login'") or die ("Запрос к базе завершился ошибкой.");
			
		if ($hash == $pass) {
			//echo $hash.'|16030|'.$checksumm.'_'.$login.'|17003|'.rand(999, 9999999);
			echo $hash.'<>'.$checksumm.'<>'.$filesize.'<>'.'_'.$login.'<>'.rand(999999, 99999999);
		} else {
			echo "Bad Login";
		}
}
			
} else {
echo "Fuck Off";
}
?>