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

    if(isset($_GET['plantName'])) {
        $plantName = $_GET['plantName'];
    }

    $query=mysqli_query($con,"SELECT * FROM plantdb WHERE `plant Name` = '$plantName'");
    if($query)
    {
        while($row=mysqli_fetch_array($query))
        {
            $flag=$row;
        }

        print(json_encode($flag));
    }
    mysqli_close($con);
    ?>