<?php

	$dbname = $_POST['dbname'];
	$dbuser = $_POST['dbuser'];
	$dbpass = $_POST['dbpass'];

	$conn=mysqli_connect("localhost", $dbuser, $dbpass, $dbname);

	if (mysqli_connect_errno($conn)) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}else{

		$username = $_POST['username'];
		$result = mysqli_query($conn,"SELECT uporabnisko_geslo FROM Oseba where uporabnisko_ime='$username'");
		$row = mysqli_fetch_array($result);
		$data = $row[0];
		
		if($data){
			echo $data;
		}
		mysqli_close($conn);
	}
?>