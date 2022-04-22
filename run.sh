# build files
echo "Build Started"
npm run build
echo "Build Completed"

# sync android project
echo "Android Sync Started"
npx cap sync android
echo "Android Sync Completed"

#open android studio
echo "Opening Android Studio"
npx cap open android
