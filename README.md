
# react-native-order-by-distance

## Getting started

`$ npm install react-native-order-by-distance --save`

### Mostly automatic installation

`$ react-native link react-native-order-by-distance`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-order-by-distance` and add `RNOrderByDistance.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNOrderByDistance.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNOrderByDistancePackage;` to the imports at the top of the file
  - Add `new RNOrderByDistancePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-order-by-distance'
  	project(':react-native-order-by-distance').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-order-by-distance/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-order-by-distance')
  	```


## Usage
```javascript
import RNOrderByDistance from 'react-native-order-by-distance';

// TODO: What to do with the module?
RNOrderByDistance;
```
  