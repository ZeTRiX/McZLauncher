<?php
if(!defined('MZLwp')) die('403 Forbidden');

$db_host		= 'localhost';
$db_port		=  3306;
$db_user		= 'root';
$db_pass		= 'password';
$db_database	= 'database_name';

$db_table		= 'mzl_table_1';
$db_id			= 'id';
$db_username	= 'user';
$db_mail		= 'mail';
$db_password	= 'hash';
$db_seckey		= 'seckey';
$db_usrhost		= 'hostip';

$link = @mysql_connect($db_host.':'.$db_port,$db_user,$db_pass) or die('Can\'t connect to the database!');

mysql_select_db($db_database, $link);
mysql_query("SET names UTF8");
?>