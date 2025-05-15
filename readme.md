# Expense Management System

A robust Spring Boot application for managing personal expenses with features like user authentication, expense tracking, budget alerts, and a comprehensive dashboard.

## Key Features

- **User Management**
  - Secure registration and login with JWT authentication
  - Personal profile management
  - Role-based access control

- **Expense Tracking**
  - Add, edit, and delete expenses
  - Categorize expenses (e.g., food, transport, utilities)
  - Add notes and attachments to expenses
  - Track payment methods
  - View expense history

- **Dashboard Analytics**
  - Monthly expense summary
  - Category-wise expense breakdown
  - Visual charts and graphs
  - Spending trends analysis
  - Budget vs. actual comparison

- **Budget Management**
  - Set monthly/category-wise budgets
  - Real-time budget tracking
  - Automated alerts when nearing budget limits
  - Budget adjustment recommendations

- **Notifications**
  - Email alerts for budget thresholds
  - Scheduled reminders for recurring expenses
  - Custom notification preferences
  - Real-time payment due alerts

- **Security Features**
  - JWT-based authentication
  - Password encryption
  - Secure API endpoints
  - Session management

## Technology Stack

- **Backend Framework:** Spring Boot
- **Security:** Spring Security with JWT
- **Database:** JPA/Hibernate
- **Build Tool:** Maven
- **Java Version:** 17 (or later)

## Detailed Setup Guide

### Prerequisites

1. **Java Development Kit (JDK) 17 or later**
   - Download from [Oracle's website](https://www.oracle.com/java/technologies/downloads/)
   - Set JAVA_HOME environment variable

2. **Maven 3.6+**
   - Download from [Maven's website](https://maven.apache.org/download.cgi)
   - Add Maven to system PATH

3. **MySQL 8.0 or later**
   - Download from [MySQL website](https://dev.mysql.com/downloads/mysql/)
   - Install MySQL Server
   - Install MySQL Workbench (optional, for database management)
   - Create a new database:
     ```sql
     CREATE DATABASE expense_db;
     ```

### Database Setup

1. Configure your database settings in `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/expense_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### Installation Steps

1. Clone the repository:
```bash
git clone [repository-url]
```

2. Navigate to the project directory:
```bash
cd Application
```

3. Install dependencies:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`

## Using the Application

### 1. User Registration and Login
- Access the registration page at `/api/auth/register`
- Create an account with your email and password
- Login at `/api/auth/login` to receive your JWT token

### 2. Managing Expenses
- Use the dashboard to view your expense overview
- Add new expenses through the 'Add Expense' form
- View and edit expenses in the expense list
- Filter expenses by date, category, or amount
- Export expense reports in CSV/PDF format

### 3. Budget Management
- Set up monthly budgets in the budget section
- Configure category-wise budget limits
- Enable budget alerts
- View budget vs actual spending reports

### 4. Notifications Setup
- Configure notification preferences in user settings
- Set up email notifications for budget alerts
- Enable/disable specific notification types

## API Documentation

### Public Endpoints
- `POST /api/auth/register` - Register new user
  - Required fields: email, password, name
- `POST /api/auth/login` - User login
  - Required fields: email, password

### Protected Endpoints
- `GET /api/dashboard` - Get dashboard statistics
- `GET /api/expenses` - List all expenses
- `POST /api/expenses` - Create new expense
- `PUT /api/expenses/{id}` - Update expense
- `DELETE /api/expenses/{id}` - Delete expense
- `GET /api/user/profile` - Get user profile

### Authentication
Include JWT token in request header:
```
Authorization: Bearer [your-jwt-token]
```

### Detailed API Endpoints

#### 1. User Authentication

##### Register New User
```http
POST /api/auth/register
Content-Type: application/json

{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "password": "securePassword123"
}

Response (200 OK):
{
    "status": "success",
    "message": "User registered successfully",
    "userId": "123"
}
```

##### Login
```http
POST /api/auth/login
Content-Type: application/json

{
    "email": "john.doe@example.com",
    "password": "securePassword123"
}

Response (200 OK):
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "userId": "123",
    "name": "John Doe"
}
```

#### 2. Expense Management

##### Get All Expenses
```http
GET /api/expenses
Authorization: Bearer [your-jwt-token]

Response (200 OK):
{
    "expenses": [
        {
            "id": "1",
            "description": "Grocery Shopping",
            "amount": 150.50,
            "category": "FOOD",
            "date": "2025-05-15",
            "notes": "Monthly groceries",
            "paymentMethod": "CREDIT_CARD"
        }
    ]
}
```

##### Create New Expense
```http
POST /api/expenses
Authorization: Bearer [your-jwt-token]
Content-Type: application/json

{
    "description": "Internet Bill",
    "amount": 59.99,
    "category": "UTILITIES",
    "date": "2025-05-15",
    "notes": "Monthly internet subscription",
    "paymentMethod": "BANK_TRANSFER"
}

Response (201 Created):
{
    "id": "2",
    "message": "Expense created successfully"
}
```

##### Update Expense
```http
PUT /api/expenses/{id}
Authorization: Bearer [your-jwt-token]
Content-Type: application/json

{
    "description": "Internet Bill",
    "amount": 65.99,
    "category": "UTILITIES",
    "date": "2025-05-15",
    "notes": "Monthly internet subscription - Price updated",
    "paymentMethod": "BANK_TRANSFER"
}

Response (200 OK):
{
    "message": "Expense updated successfully"
}
```

##### Delete Expense
```http
DELETE /api/expenses/{id}
Authorization: Bearer [your-jwt-token]

Response (200 OK):
{
    "message": "Expense deleted successfully"
}
```

#### 3. Dashboard APIs

##### Get Dashboard Statistics
```http
GET /api/dashboard
Authorization: Bearer [your-jwt-token]

Response (200 OK):
{
    "totalExpenses": 1250.75,
    "monthlyBreakdown": {
        "FOOD": 450.00,
        "UTILITIES": 200.00,
        "TRANSPORT": 150.00,
        "OTHERS": 450.75
    },
    "budgetStatus": {
        "totalBudget": 2000.00,
        "remaining": 749.25,
        "alerts": []
    }
}
```

##### Get User Profile
```http
GET /api/user/profile
Authorization: Bearer [your-jwt-token]

Response (200 OK):
{
    "id": "123",
    "name": "John Doe",
    "email": "john.doe@example.com",
    "preferences": {
        "currency": "USD",
        "notifications": {
            "emailAlerts": true,
            "budgetWarnings": true
        }
    }
}
```

#### 4. Budget Management

##### Set Budget
```http
POST /api/budget
Authorization: Bearer [your-jwt-token]
Content-Type: application/json

{
    "monthlyBudget": 2000.00,
    "categoryBudgets": {
        "FOOD": 500.00,
        "UTILITIES": 300.00,
        "TRANSPORT": 200.00
    }
}

Response (200 OK):
{
    "message": "Budget updated successfully"
}
```

### Error Responses

All endpoints may return the following error responses:

```http
401 Unauthorized
{
    "error": "Invalid or expired token"
}

400 Bad Request
{
    "error": "Invalid input",
    "details": ["Amount must be positive", "Category is required"]
}

404 Not Found
{
    "error": "Resource not found"
}

500 Internal Server Error
{
    "error": "Internal server error",
    "message": "Something went wrong"
}
```

### Making API Calls

#### Using cURL

1. Login:
```powershell
$body = @{
    email = "john.doe@example.com"
    password = "securePassword123"
} | ConvertTo-Json

curl -X POST `
  -H "Content-Type: application/json" `
  -d $body `
  http://localhost:8080/api/auth/login
```

2. Create Expense:
```powershell
$token = "your-jwt-token"
$body = @{
    description = "Grocery Shopping"
    amount = 150.50
    category = "FOOD"
    date = "2025-05-15"
} | ConvertTo-Json

curl -X POST `
  -H "Authorization: Bearer $token" `
  -H "Content-Type: application/json" `
  -d $body `
  http://localhost:8080/api/expenses
```

#### Using Postman

1. Import the provided Postman collection (available in the project root)
2. Set up environment variables:
   - `baseUrl`: http://localhost:8080
   - `token`: Your JWT token after login
3. Use the pre-configured requests in the collection

## Troubleshooting

Common issues and solutions:
1. Database connection issues
   - Verify database credentials
   - Ensure database server is running
   
2. JWT token expired
   - Re-login to get a new token
   - Check token expiration time in settings

3. Build failures
   - Run `mvn clean install -U` to force update dependencies
   - Check Java version compatibility

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details

## Support

For support and queries, please create an issue in the repository or contact the development team.