#!/bin/bash

# Navigate to the Back-end directory
cd /workspaces/Vehicle_Rental_Application_FS/Back-end

# Clean and compile the project
echo "Cleaning and compiling the project..."
mvn clean compile

# Check if the compilation was successful
if [ $? -eq 0 ]; then
    echo "Compilation successful! The modernization is working properly."
    
    # Run tests if specified
    if [ "$1" == "--test" ]; then
        echo "Running tests..."
        mvn test
    fi
else
    echo "Compilation failed. Please check the error messages above."
fi
