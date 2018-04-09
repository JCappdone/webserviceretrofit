<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "users";

//create connection
$conn = new mysqli($servername,$username,$password,$dbname);
//check connection
if($conn->connect_error){
  die("Connection failed: " . $conn->connect_error);
}

$flag = $_REQUEST['type'];
switch($flag)
{
  case '1':
    insertUser($conn);
    break;

  case '2':
    updateUserById($conn);
    break;

  case '3':
    deleteUserById($conn);
    break;

  case '4':
    getAllUser($conn);
    break;

  case '5':
    undoAllUser($conn);
    break;

}

function insertUser($conn)
{
  $name = $_REQUEST['name'];
  $phone = $_REQUEST['phone'];
  $image = $_REQUEST['image'];

  $sql = "INSERT INTO userTable (name,phone,image) VALUES ('$name','$phone','$image')";

  if ($conn->query($sql) === TRUE){
//post    echo "{\"New record created successfully\"}";
    echo json_encode("New record created successfully");
  }else{
    echo json_encode("Error: " . $sql . "<br>" . $conn->error);
  }
}

function getAllUser($conn)
{
  $sql = "SELECT * FROM userTable WHERE status='Enable'";

  $result = $conn->query($sql);
  if($result->num_rows>0){
  while ($row = $result->fetch_assoc()) {
    $data[] = $row;
//    $data['result'][] = $row;
    }
    echo json_encode($data);
  }else{
    $data['result']="0 results";
    echo json_encode($data);
  }
}

function deleteUserById($conn)
{
  $id = $_REQUEST['id'];

  $sql = "UPDATE userTable SET status='Disable' WHERE id=$id";
  //echo $conn->query($sql);
  if($conn->query($sql) === TRUE){
      echo json_encode("Record deleted successfully");
  }else{
      echo json_encode("Error: " . $sql . "<br>" . $conn->error);
  }
}

function undoAllUser($conn)
{
  $sql = "UPDATE userTable SET status='Enable'";
  //echo $conn->query($sql);
  if($conn->query($sql) === TRUE){
      echo json_encode("Undo All successfully");
  }else{
      echo json_encode("Error: " . $sql . "<br>" . $conn->error);
  }
}



function updateUserById($conn)
{
  $id = $_REQUEST['id'];
  $name = $_REQUEST['name'];
  $phone = $_REQUEST['phone'];
  $image = $_REQUEST['image'];

  $sql = "UPDATE userTable SET name='$name',phone='$phone',image='$image' WHERE id=$id";

  if($conn->query($sql) === TRUE){
      echo json_encode("Record Update successfully");
  }else{
      echo json_encode("Error: " . $sql . "<br>" . $conn->error);
  }
}
?>
