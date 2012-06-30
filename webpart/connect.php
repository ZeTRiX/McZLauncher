<?php
if(!defined('MZL')) die('403 Forbidden');

$db_host		= 'localhost';
$db_port		=  3306;
$db_user		= 'root';
$db_pass		= 'pass';
$db_database	= 'mine_db';

$db_table = 'mzl_table_1';
$db_id = 'id';
$db_username = 'user';
$db_password = 'hash';
$db_seckey = 'seckey';

$link = @mysql_connect($db_host.':'.$db_port,$db_user,$db_pass) or die('Невозможно установить соединение с базой данных!');

mysql_select_db($db_database, $link);
mysql_query("SET names UTF8");
?>