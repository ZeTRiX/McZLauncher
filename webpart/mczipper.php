<?php
define('MZLwp',true);
include ("connect.php");
$mczip = trim($_POST['mczip']);

if ((preg_match('/'.$secversion.'/i', $_SERVER['HTTP_USER_AGENT']))) {
unlink($mczip);
$zip = new ZipArchive();
$zip->open($mczip, ZipArchive::CREATE);
$killlength = (strlen($mczipper)) + 1;

 if (!is_dir($mczipper)) {
     throw new Exception('Directory ' . $mczipper . ' does not exist');
 }

$mczipper = realpath($mczipper);
 if (substr($mczipper, -1) != '/') {
     $mczipper.= '/';
 }

$dirStack = array($mczipper);
//Find the index where the last dir starts
$cutFrom = strrpos(substr($mczipper, 0, -1), '/')+1;

while (!empty($dirStack)) {
    $currentDir = array_pop($dirStack);
    $filesToAdd = array();

    $dir = dir($currentDir);
    while (false !== ($node = $dir->read())) { 
        if (($node == '..') || ($node == '.')) { 
            continue;
        } 
        if (is_dir($currentDir . $node)) { 
            array_push($dirStack, $currentDir . $node . '/');
        }
        if (is_file($currentDir . $node)) {
            $filesToAdd[] = $node;
        }
    }
	 

    $localDir = substr($currentDir, $cutFrom);
    $localDir = substr($localDir, $killlength);
    //$zip->addEmptyDir($localDir);
     
    foreach ($filesToAdd as $file) {
        $zip->addFile($currentDir . $file, $localDir . $file);
    }
}

$zip->close();
die ('Success');

} else {
	die ('Fuck Off');
}
?>