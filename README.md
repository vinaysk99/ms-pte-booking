### **Overview**

ms-pte-booking is a sample seed gateway that defines :
* A micro-service to book for pte
* admins to view and accept all the bookings

### **API**

#### **POST /v1/bookingDetails**
Create new booking
###### Request
> Body :
```
{
  "firstName": "u1",
  "lastName": "CKj",
  "emailId": "sdfsd@fdsf.com",
  "phoneNumber": "23423432",
  "address": "23, dfhjk st, dhjfk, nsw, 2112",
  "bookingLocation": "Parramatta",
  "bookingDate": "2018-09-02",
  "bookingTime": "1530"
}
```

###### Response
> Code : 201  
> Body :
```
{
    "data": {
      "firstName": "u1",
      "lastName": "CKj",
      "emailId": "sdfsd@fdsf.com",
      "phoneNumber": "23423432",
      "address": "23, dfhjk st, dhjfk, nsw, 2112",
      "bookingLocation": "Parramatta",
      "bookingDate": "2018-09-02",
      "bookingTime": "1530"
    }
}
```

#### **PUT /v1/bookingDetails/{bookingId}**
Update booking
###### Request
> Body :
```
{
  "firstName": "u1",
  "lastName": "CKj",
  "emailId": "sdfsd@fdsf.com",
  "phoneNumber": "23423432",
  "address": "23, dfhjk st, dhjfk, nsw, 2112",
  "bookingLocation": "Parramatta",
  "bookingDate": "2018-09-02",
  "bookingTime": "1530"
}
```

###### Response
> Code : 200
> Body :
```
{
    "data": {
      "id": "ex1",
      "firstName": "u1",
      "lastName": "CKj",
      "emailId": "sdfsd@fdsf.com",
      "phoneNumber": "23423432",
      "address": "23, dfhjk st, dhjfk, nsw, 2112",
      "bookingLocation": "Parramatta",
      "bookingDate": "2018-09-02",
      "bookingTime": "1530"
    }
}
```

#### **GET /v1/bookingDetails/{bookingId}**
Retrieve matching `BookingDetails` object for `bookingId`
###### Response 
> Code : 200  
> Body :
```
{
    "data": {
      "id": "ex1",
      "firstName": "u1",
      "lastName": "CKj",
      "emailId": "sdfsd@fdsf.com",
      "phoneNumber": "23423432",
      "address": "23, dfhjk st, dhjfk, nsw, 2112",
      "bookingLocation": "Parramatta",
      "bookingDate": "2018-09-02",
      "bookingTime": "1530"
    }
}
```

#### **GET /v1/bookingDetails?userId={userId}**
Retrieve matching `BookingDetails` objects for `userId`
###### Response
> Code : 200
> Body :
```
{
    "data": [
        {
          "id": "ex1",
          "firstName": "u1",
          "lastName": "CKj",
          "emailId": "sdfsd@fdsf.com",
          "phoneNumber": "23423432",
          "address": "23, dfhjk st, dhjfk, nsw, 2112",
          "bookingLocation": "Parramatta",
          "bookingDate": "2018-09-02",
          "bookingTime": "1530"
        }
    ]
}
```

#### **DELETE /v1/bookingDetails/{bookingId}**
Call ms to delete `BookingDetails` object for `bookingId`
###### Response
> Code : 200

