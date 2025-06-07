# INTEREST CALCULATOR

## How to build 

### FE development only

If you want to just see the UI, go inside the webapp folder and launch `npm start`. This will use webpack to launch a
web server and put inside your js code. MirageJs is used to mock rest calls.

### FE pointing to BE

- Build for development: inside the webapp application, run `npm run start:local`: this will generate the js executables inside
  the resources/static folder. Then launch the application using IDEA. Actually, the npm command just produces the minified
  js that will be imported in the .html file.. So there is no need to keep it active after compilation. Then run the Java app.
- Build for production (not useful for development, is something the pipeline will take care of) run `mvn clean package`
  and then launch the jar inside the webapp/target folder

IMPORTANT: at the moment, `mvn clean package` bundles the FE application to run in "production" environment only. So,
if you run it and then try to run the application locally, you will see the application pointing to prod environment
(taken from the /target folder built before using maven).

## CI/CD

The project is integrated with Github actions. You can find the configuration inside the .github folder.

## Deploy

Run the script ./run.sh. This will run the app with the production configuration.