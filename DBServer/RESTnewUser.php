<?php

	$dbname = $_POST['dbname'];
	$dbuser = $_POST['dbuser'];
	$dbpass = $_POST['dbpass'];

	$conn=mysqli_connect("localhost", $dbuser, $dbpass, $dbname);

	if (mysqli_connect_errno($conn)) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}else{
		mysqli_autocommit($conn, FALSE);
		
		$insertString = $_POST['insertString'];
		$result = mysqli_query($conn, $insertString);
		
		mysqli_commit($conn);
		$data = mysqli_fetch_row(mysqli_query($conn, "SELECT MAX( id ) FROM Oseba"))[0];
		
		echo $data;
		mysqli_close($conn);
	}
?>