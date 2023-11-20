# Joke App
## Introduction
Welcome to the Joke App, a fun and interactive application designed to bring a smile to your day with a collection of safe-for-work jokes. Built using modern reactive programming techniques, our app ensures a smooth and responsive user experience, even under high load. Whether you're looking for a quick chuckle or a series of laughs, our Joke App is here to lighten your mood!

## Features
- **Safe-for-Work Jokes**: Enjoy a curated collection of jokes that are appropriate for all audiences.
- **Reactive Backend**: Built with a reactive framework for efficient handling of multiple simultaneous requests.
- **Simple Interface**: Easy-to-use interface ensuring a seamless user experience.
## Testing the App in Parallel
To ensure our app can handle multiple requests simultaneously, we've set up a method to test it under load using parallel curl requests. This is particularly useful for understanding how the app performs under stress and helps in identifying any potential bottlenecks.

### Setting Up Parallel Testing
Create a Directory for Test Outputs:
To keep our project organized, we'll first create a directory specifically for storing the outputs of our test requests.

```bash
mkdir -p curl
cd curl
```
### Run Parallel cURL Requests:
Execute the following command in the curl directory to send 500 curl requests to the app. These requests are sent in batches of 50 running in parallel, with each request's output saved to a separate file.

```markdown
seq 500 | xargs -n1 -P50 -I {} sh -c 'curl http://localhost:8080/api/jokes/safe > output_{}.txt'
```

This command will create **500 files** named `output_1.txt`, `output_2.txt`, ..., `output_500.txt`, each containing the response from one of the requests.
### Note: 
Running this test will generate a significant number of files and can put considerable load on the server. It's recommended to perform this test in a controlled environment and ensure that your server and network setup can handle this level of traffic.