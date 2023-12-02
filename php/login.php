<?php
// login.php - Handle the login request

// Include your database connection settings
include('db_config.php');

// Collect email and password from the request
$email = $_POST['email'] ?? '';
$password = $_POST['password'] ?? '';

// Prepare a statement to prevent SQL injection
$stmt = $conn->prepare("SELECT * FROM user WHERE email = ?");

// Bind the email parameter
$stmt->bind_param("s", $email);

// Execute the statement
$stmt->execute();

// Store the result so we can check if the account exists in the database.
$stmt->store_result();

if ($stmt->num_rows > 0) {
    // Bind the result to variables
    $stmt->bind_result($id, $firstName, $lastName, $email, $db_password, $profile_pic);

    // Fetch the details of the user
    $stmt->fetch();

    // Verify the password (assuming the password in the database is hashed)
    if (password_verify($password, $db_password)) {
        // Success! Login credentials are correct.
        
        // Start the session
        session_start();

        // Set session variables
        $_SESSION['user_id'] = $id;
        $_SESSION['email'] = $email;
        
        // Return a success response
        echo json_encode(["status" => "success", "message" => "Login successful"]);
    } else {
        // Invalid credentials
        echo json_encode(["status" => "error", "message" => "Invalid credentials"]);
    }
} else {
    // Account doesn't exist
    echo json_encode(["status" => "error", "message" => "Account doesn’t exist"]);
}

// Close the statement
$stmt->close();

// Close the database connection
$conn->close();
?>
