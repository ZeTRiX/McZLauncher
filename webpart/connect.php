<?php
if(!defined('MZLwp')) die('403 Forbidden');

$db_host = 'localhost';
$db_port =  3306;
$db_user = 'db_username';
$db_pass = 'password';
$db_database = 'db_datbase';

$db_table = 'mzl_table_1';
$db_id = 'id';
$db_username = 'user';
$db_mail = 'mail';
$db_password = 'hash';
$db_seckey = 'seckey';
$db_usrhost = 'hostip';

$masteraddr = '127.0.0.1'; //MasterServer IP address
$mczipper = 'mczipper';

$link = @mysql_connect($db_host.':'.$db_port,$db_user,$db_pass) or die('Невозможно установить соединение с базой данных!');

mysql_select_db($db_database, $link);
mysql_query("SET names UTF8");
?>