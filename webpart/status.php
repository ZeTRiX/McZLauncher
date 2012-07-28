<?php
if(!defined('MZLwp')) die('403 Forbidden');

class status {
	private $Socket, $Info;
	public $Online, $MOTD, $CurPlayers, $MaxPlayers, $IP, $Port, $Error;
	
	public function __construct($IP, $Port = '25565') {
		$this->IP = $IP;
		$this->Port = $Port;

		if(preg_match('/(.*):\/\//', $this->IP)) $this->IP = preg_replace('/(.*):\/\//', '', $this->IP);
		
		if(strpos($this->IP, '/') !== false) {
			$this->IP = rtrim($this->IP, '/');
			if(strpos($this->IP, '/') !== false) {
				$this->Failed();
				$this->Error = 'Unsupported domain';
				return;
			}
		}
		if(preg_match_all('/:/', $this->IP, $matches) > 1) {
			unset($matches);
			if(strpos($this->IP, '[') === false && strpos($this->IP, ']') === false) $this->IP = '['.$this->IP.']';
		} else if(strpos($this->IP, ':') !== false)	{
			$this->Failed();
			$this->Error = 'Incorrect domain';
			return;
		}
		
		if($this->Socket = @stream_socket_client('tcp://'.$this->IP.':'.$Port, $ErrNo, $ErrStr, 1)) {
			if(strpos($this->IP, '[') === 0 && strpos($this->IP, ']') === (strlen($this->IP) - 1)) $this->IP = trim($this->IP, '[]');
			
			$this->Online = true;
			
			fwrite($this->Socket, "\xfe");
			$Handle = fread($this->Socket, 2048);
			$Handle = str_replace("\x00", '', $Handle);
			$Handle = substr($Handle, 2);
			$this->Info = explode("\xa7", $Handle);
			unset($Handle);
			fclose($this->Socket);
			
			if(sizeof($this->Info) == 3) {
				$this->MOTD       = $this->Info[0];
				$this->CurPlayers = (int)$this->Info[1];
				$this->MaxPlayers = (int)$this->Info[2];
				$this->Error      = false;
			} else if(sizeof($this->Info) > 3) {
				$Temp = '';
				for($i = 0; $i < sizeof($this->Info) - 2; $i++)	{
					$Temp .= ($i > 0 ? '§' : '').$this->Info[$i];
				}
				$this->MOTD       = $Temp;
				$this->CurPlayers = (int)$this->Info[sizeof($this->Info) - 2];
				$this->MaxPlayers = (int)$this->Info[sizeof($this->Info) - 1];
				$this->Error      = 'Can\'t connect.';
			} else {
				$this->Failed();
				$this->Error      = 'Unexpected error, cause may be an outdated script.';
			}
		} else {
			$this->Online = false;
			$this->Failed();
			$this->Error = 'Server is offline';
		}
	}
	
	public function Info() {
		return array
		(
			'MOTD'       => $this->MOTD,
			'CurPlayers' => $this->CurPlayers,
			'MaxPlayers' => $this->MaxPlayers
		);
	}
	
	private function Failed() {
		$this->MOTD       = false;
		$this->CurPlayers = false;
		$this->MaxPlayers = false;
	}
}
?>