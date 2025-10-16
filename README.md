# 📋 Billing Software - Spring Boot REST API

A comprehensive billing and inventory management system built with Spring Boot, featuring JWT authentication, payment integration, and cloud storage capabilities.

## 🚀 Features

- **User Management**
  - JWT-based authentication & authorization
  - Role-based access control (Admin/User)
  - Secure password encryption with BCrypt
  - Soft delete functionality

- **Inventory Management**
  - Category management with image uploads
  - Item management with detailed information
  - Cloud-based image storage (Cloudinary)

- **Order Processing**
  - Complete order lifecycle management
  - Support for multiple payment methods (Cash/UPI)
  - Order tracking and history

- **Payment Integration**
  - Razorpay payment gateway integration
  - Payment verification and signature validation
  - Real-time payment status updates

- **Dashboard Analytics**
  - Daily sales summaries
  - Order statistics
  - Recent order tracking

## 🛠️ Tech Stack

- **Backend Framework:** Spring Boot 3.3.0
- **Language:** Java 17
- **Database:** MySQL
- **Security:** Spring Security + JWT (JJWT 0.12.6)
- **Payment Gateway:** Razorpay
- **Cloud Storage:** Cloudinary
- **Build Tool:** Maven
- **Additional Libraries:**
  - Lombok (code generation)
  - Spring Data JPA
  - Spring Validation
  - Hibernate

## 📋 Prerequisites

- Java 17 or higher
- MySQL 8.0+
- Maven 3.6+
- Cloudinary account (for image storage)
- Razorpay account (for payment processing)

## ⚙️ Installation & Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd billingsoftware
```

### 2. Configure Database

Create a MySQL database:

```sql
CREATE DATABASE BillingApp;
```

### 3. Set Environment Variables

Create environment variables or update `application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/BillingApp
spring.datasource.username=your_username
spring.datasource.password=your_password

# Cloudinary Configuration
CLOUDINARY_CLOUD_NAME=your_cloud_name
CLOUDINARY_API_KEY=your_api_key
CLOUDINARY_API_SECRET=your_api_secret

# Razorpay Configuration
MY_API_KEY_ID=your_razorpay_key_id
MY_API_KEY_SECREATE=your_razorpay_secret
MY_API_URL=https://api.razorpay.com/v1/

# JWT Secret Key (Base64 encoded, min 256 bits)
MY_JWT_KEY=your_base64_encoded_secret_key
```

### 4. Build the Project

```bash
./mvnw clean install
```

### 5. Run the Application

```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080/api/v1.0`

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api/v1.0
```

### Authentication Endpoints

#### Register User
```http
POST /register
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "USER"
}
```

#### Login
```http
POST /login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "email": "john@example.com",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "role": "USER"
}
```

### Category Endpoints

#### Add Category (Admin Only)
```http
POST /admin/categories
Authorization: Bearer <token>
Content-Type: multipart/form-data

category: {
  "name": "Electronics",
  "description": "Electronic items",
  "bgColor": "#FF5733"
}
file: <image_file>
```

#### Get All Categories
```http
GET /categories
Authorization: Bearer <token>
```

#### Delete Category (Admin Only)
```http
DELETE /admin/categories/{categoryId}
Authorization: Bearer <token>
```

### Item Endpoints

#### Add Item (Admin Only)
```http
POST /admin/items
Authorization: Bearer <token>
Content-Type: multipart/form-data

item: {
  "name": "Laptop",
  "description": "Gaming Laptop",
  "price": 1500.00,
  "categoryId": "uuid-here"
}
file: <image_file>
```

#### Get All Items
```http
GET /items
Authorization: Bearer <token>
```

#### Delete Item (Admin Only)
```http
DELETE /admin/items/{itemId}
Authorization: Bearer <token>
```

### Order Endpoints

#### Create Order
```http
POST /orders
Authorization: Bearer <token>
Content-Type: application/json

{
  "customerName": "John Doe",
  "phoneNumber": "1234567890",
  "cartItems": [
    {
      "itemId": "item-uuid",
      "name": "Laptop",
      "price": 1500.00,
      "quantity": 1
    }
  ],
  "subTotal": 1500.00,
  "tax": 15.00,
  "grandTotal": 1515.00,
  "paymentMethod": "CASH"
}
```

#### Get Latest Orders
```http
GET /orders/latest
Authorization: Bearer <token>
```

#### Delete Order
```http
DELETE /orders/{orderId}
Authorization: Bearer <token>
```

### Payment Endpoints

#### Create Razorpay Order
```http
POST /payment/create-order
Authorization: Bearer <token>
Content-Type: application/json

{
  "amount": 1515.00,
  "currency": "INR"
}
```

#### Verify Payment
```http
POST /payment/verify
Authorization: Bearer <token>
Content-Type: application/json

{
  "orderId": "order-uuid",
  "razorpayOrderId": "razorpay_order_id",
  "razorpayPaymentId": "razorpay_payment_id",
  "razorpaySignature": "signature"
}
```

### Dashboard Endpoint

#### Get Dashboard Data
```http
GET /dashboard
Authorization: Bearer <token>
```

**Response:**
```json
{
  "todaysSale": 5000.00,
  "totalOrders": 25,
  "recentOrders": [...]
}
```

### User Management (Admin Only)

#### Get All Users
```http
GET /admin/users
Authorization: Bearer <token>
```

#### Delete User
```http
DELETE /admin/users/{userId}
Authorization: Bearer <token>
```

### Health Check

#### Check API Health
```http
GET /health
```

## 🔐 Security

- **JWT Token Expiration:** 10 hours
- **Password Encryption:** BCrypt with Spring Security
- **CORS Configuration:** Configured for `http://localhost:5173`
- **Role-Based Access:** 
  - `ADMIN`: Full access to all endpoints
  - `USER`: Limited access to non-admin endpoints

### Adding Authorization Header

All protected endpoints require a JWT token:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

## 🗄️ Database Schema

### Main Tables

- `tbl_user` - User accounts and authentication
- `tbl_category` - Product categories
- `tbl_item` - Inventory items
- `tbl_orders` - Order information
- `tbl_orders_items` - Order line items

### Entity Relationships

```
UserEntity (1) -----> (*) OrderEntity
CategoryEntity (1) -----> (*) ItemEntity
OrderEntity (1) -----> (*) OrderItemEntity
```

## 🎨 Frontend Integration

The API is designed to work with a frontend application running on `http://localhost:5173`.

**CORS Configuration:**
- Allowed Origins: `http://localhost:5173`
- Allowed Methods: GET, POST, PUT, DELETE, OPTIONS
- Credentials: Enabled

## 📝 Development Notes

### First User Setup

The first registered user will automatically be assigned the `ADMIN` role. All subsequent users will have the `USER` role by default for security purposes.

### Image Upload

All images are stored on Cloudinary. Ensure you have valid credentials configured in the environment variables.

### Payment Testing

For Razorpay integration testing, use Razorpay test mode credentials.

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Verify MySQL is running
   - Check database credentials in `application.properties`

2. **JWT Token Issues**
   - Ensure `MY_JWT_KEY` is Base64 encoded and at least 256 bits
   - Check token expiration (10 hours)

3. **Image Upload Failures**
   - Verify Cloudinary credentials
   - Check file size limits

4. **CORS Errors**
   - Ensure frontend URL matches CORS configuration
   - Check if preflight OPTIONS requests are allowed

## 📄 License

This project is for educational purposes.

## 👥 Authors

- **Parth** - Initial work

## 🙏 Acknowledgments

- Spring Boot Team
- Razorpay
- Cloudinary
- All open-source contributors

---

**Note:** Remember to never commit sensitive information like API keys, database passwords, or JWT secrets to version control. Always use environment variables or secure configuration management.
