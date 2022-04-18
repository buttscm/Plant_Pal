<?php

    $host='localhost';
    $username='tmp';
    $pwd='tmp';
    $db="plant";

    $con=mysqli_connect($host,$username,$pwd,$db) or die('Unable to connect');

    if(mysqli_connect_errno())
    {
        echo "Failed to Connect to Database ".mysqli_connect_error();
    }

    if(isset($_GET['id'])) { $id = $_GET['id']; }
    if(isset($_GET['plantName'])) { $plantName = $_GET['plantName']; }
    if(isset($_GET['moistureThreshold'])) { $moistureThreshold = $_GET['moistureThreshold']; }
    if(isset($_GET['sunlightThreshold'])) { $sunlightThreshold = $_GET['sunlightThreshold']; }

    $query = mysqli_query($con,"UPDATE usersetting SET id='$id', SelectedPlant='$plantName',
    MoistureThreshold='$moistureThreshold',SunlightThreshold='$sunlightThreshold'");

    if($query) {
        echo "successful update!";
    } else {
        echo "update unsuccessful...";
    }
    mysqli_close($con);
    ?>