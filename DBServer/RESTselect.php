<?php

	$dbname = $_POST['dbname'];
	$dbuser = $_POST['dbuser'];
	$dbpass = $_POST['dbpass'];

	$conn=mysqli_connect("localhost", $dbuser, $dbpass, $dbname);

	if (mysqli_connect_errno($conn)) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}else{

		$selectString = $_POST['selectString'];
		$result = mysqli_query($conn, $selectString);
		$data = "";
		while(true){
			$row = mysqli_fetch_row($result);
			
			if($row == NULL)
				break;
			
			// Array to string with spaces in between
			$data = $data . implode(" ", $row) . "\n";
		}
  		
		echo $data;
		mysqli_close($conn);
	}
?>