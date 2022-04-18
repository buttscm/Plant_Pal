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

    if(isset($_GET['moistureThreshold'])) { $moistureThreshold = $_GET['moistureThreshold']; }
    if(isset($_GET['sunlightThreshold'])) { $sunlightThreshold = $_GET['sunlightThreshold']; }
    if(isset($_GET['autoWatering'])) { $autoWatering = $_GET['autoWatering']; }

    $query = mysqli_query($con,"UPDATE usersetting SET `MoistureThreshold` ='$moistureThreshold', `SunlightThreshold` ='$sunlightThreshold',
    AutomaticWater ='$autoWatering'");

    if($query) {
        echo "successful update!";
    } else {
        echo "update unsuccessful...";
    }
    mysqli_close($con);
    ?>