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
$seckey=mysql_real_escape_string(intval($_POST['opt']));

if(preg_match('/ZeTRiX/i',$_SERVER['HTTP_USER_AGENT'])) {
			$result = mysql_query("SELECT $db_password FROM `$db_table` WHERE $db_username='$login'") or die ("Запрос к базе завершился ошибкой.");
    		$row = mysql_fetch_array($result);

			$hash = $row[$db_password];
			$checksumm = sha1(file_get_contents("minecraft.jar"));
			
			$query = mysql_query("UPDATE $db_table SET `seckey` = '$seckey' WHERE $db_username='$login'") or die ("Запрос к базе завершился ошибкой.");
			
			if ($hash == $pass) {
			//echo $hash.'|16030|'.$checksumm.'_'.$login.'|17003|'.rand(999, 9999999);
			echo $hash.'<>'.$checksumm.'_'.$login.'<>'.rand(99999, 9999999);
			} else {
			echo "Bad Login";
			}
} else {
echo "Fuck Off";
}
?>