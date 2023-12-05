<?php
include("conn.php");

// Check if the request method is POST
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Prepare and bind
    $stmt = $conn->prepare("INSERT INTO items (name, hourlyrate, description, city, imgurl, videourl) VALUES (?, ?, ?, ?, ?, ?)");
    $stmt->bind_param("ssssss", $name, $hourlyrate, $description, $city, $imgurl, $videourl);

    // Parameters from POST request
    $name = $_POST['name'];
    $hourlyrate = $_POST['hourlyrate'];
    $description = $_POST['description'];
    $city = $_POST['city'];
    $imgurl = $_POST['imgurl'];
    $videourl = $_POST['videourl'];

    // Execute and respond
    if ($stmt->execute()) {
        echo "New record created successfully";
    } else {
        echo "Error: " . $stmt->error;
    }

    $stmt->close();
} else {
    echo "Invalid request method.";
}

$conn->close();
?>
