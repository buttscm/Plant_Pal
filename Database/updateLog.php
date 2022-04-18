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

    $query=mysqli_query($con,"UPDATE usersetting SET ForceLog = 1");
    if($query)
    {
        echo "successful update!";
    } else {
        echo "update unsuccessful...";
    }
    mysqli_close($con);
    ?>