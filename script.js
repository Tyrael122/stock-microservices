import http from 'k6/http';
import { check, sleep } from 'k6';

// Define the base URLs for the respective services
const USER_SERVICE_URL = 'http://localhost:41943/users';
const ORDER_SERVICE_URL = 'http://localhost:37013/orders';

export let options = {
  stages: [
    { duration: '10s', target: 500 }, // Ramp up to 50 virtual users over 30 seconds
    { duration: '40s', target: 1000 }, // Ramp up to 100 virtual users over 1 minute
    { duration: '10s', target: 0 },  // Ramp down to 0 virtual users over 30 seconds
  ],
};

export default function () {
  // Step 1: Create a user via the /users endpoint (Only once for each virtual user)
  const createUserPayload = JSON.stringify({
    name: 'Kauan',
    email: 'example@gmail.com',
    balance: 12,
  });

  const createUserHeaders = { 'Content-Type': 'application/json' };
  const createUserRes = http.post(USER_SERVICE_URL, createUserPayload, { headers: createUserHeaders });

  // Check if user creation was successful
  check(createUserRes, {
    'user created successfully': (res) => res.status === 200,
  });

  // Step 2: Extract the user ID from the response
  const userId = createUserRes.json('id');

  // Check if user ID was returned
  if (!userId) {
    console.error('User ID not found!');
    return;
  }

  // Step 3: Generate multiple requests to the /orders endpoint
  const buyPayload = JSON.stringify({
    userId: userId,
    stockSymbol: 'APPL12',
    quantity: 1,
    price: 1,
    type: 'BUY',
  });

  // Make multiple requests to the /orders endpoint
  for (let i = 0; i < 10; i++) {  // 10 requests per virtual user per iteration (can be adjusted)
    const buyRes = http.post(ORDER_SERVICE_URL, buyPayload, { headers: createUserHeaders });

    // Check if buying shares was successful
    check(buyRes, {
      'buy shares successful': (res) => res.status === 200,
    });

    // Sleep for a short time between requests to simulate real-world load
    sleep(0.1);  // 100ms delay between each request (can be adjusted)
  }
}
