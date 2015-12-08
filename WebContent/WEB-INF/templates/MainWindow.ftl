<html>

<#if ${username} == "admin">
<head><title>Admin Interface</title>
</head>
<body>

<h1>Welcome to RentARide Admin Management System</h1>

<h3>You are logged in as ${username}

<p>You may:
<ol>
<!--<li>
List all <a href="FindAllPersons"> persons</a>
</li>
<li>
List all <a href="FindAllClubs"> clubs</a>
</li>
<li>
List <a href="FindClubMembersClubsList"> club membership</a>
</li>
<li>
Create <a href="CreateClubPersonsList"> a new club</a>
</li>
<li>
Create <a href="CreatePerson.html">
a new person record</a>
</li>
<li>
Select a person and a club and
<a href="JoinClubPersonsClubsList"> enroll the person
in the club.</a>
</li>-->
<li>
List all <a href="FindAllRentalLocations"> rental locations</a>
</li>
<li>
Create <a href="CreateRentalLocation.html">
a new rental location record</a>
</li>
<li>
Create <a href="CreateVehicle.html"> 
a new vehicle record </a>
</li>
<li>
<a href="Logout"> Logout</a> from the RentARide system.
</li>
</ol> 

</body>
<#else>
<head><title>Customer Interface</title>
</head>
<body>

<h1>Welcome to RentARide Customer Management System</h1>

<h3>You are logged in as ${username}

<p>You may:
<ol>
<!--<li>
List all <a href="FindAllPersons"> persons</a>
</li>
<li>
List all <a href="FindAllClubs"> clubs</a>
</li>
<li>
List <a href="FindClubMembersClubsList"> club membership</a>
</li>
<li>
Create <a href="CreateClubPersonsList"> a new club</a>
</li>
<li>
Create <a href="CreatePerson.html">
a new person record</a>
</li>
<li>
Select a person and a club and
<a href="JoinClubPersonsClubsList"> enroll the person
in the club.</a>
</li>-->
<li>
List all <a href="FindAllRentalLocations"> rental locations</a>
</li>
<li>
Create <a href="CreateRentalLocation.html">
a new rental location record</a>
</li>
<li>
Create <a href="CreateVehicle.html"> 
a new vehicle record </a>
</li>
<li>
<a href="Logout"> Logout</a> from the RentARide system.
</li>
</ol> 

</body>
</#if>
</html>
