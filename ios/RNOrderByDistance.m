
#import "RNOrderByDistance.h"
#import <MapKit/MapKit.h>

@implementation RNOrderByDistance {
    CLLocationManager *locationManager;
    CLLocation *lastLocation;
}

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(startModule)
{
    locationManager = [[CLLocationManager alloc] init];
    locationManager.delegate = self;
    locationManager.desiredAccuracy = kCLLocationAccuracyBest;
    [locationManager startUpdatingLocation];
}

- (void)locationManager:(CLLocationManager *)manager
     didUpdateLocations:(NSArray<CLLocation *> *)locations
{
    lastLocation = locations.lastObject;
}

RCT_REMAP_METHOD(orderByDistance,
                 coords:(NSArray *)coords
                 withResolver:(RCTPromiseResolveBlock)resolve
                 rejecter:(RCTPromiseRejectBlock)reject)
{
    [locationManager stopUpdatingLocation];
    NSMutableDictionary *response = [[NSMutableDictionary alloc] init];
    NSArray *sortedArray = [coords sortedArrayUsingComparator:^NSComparisonResult(id a, id b) {
        NSDictionary *first = (NSDictionary*)a;
        NSDictionary *second = (NSDictionary*)b;
        double firstLatitude = [first[@"latitude"] doubleValue];
        double firstLongitude = [first[@"longitude"] doubleValue];
        
        double secondLatitude = [second[@"latitude"] doubleValue];
        double secondLongitude = [second[@"longitude"] doubleValue];
        
        CLLocation *firstLocation = [[CLLocation alloc] initWithLatitude:firstLatitude longitude:firstLongitude];
        CLLocation *secondLocation = [[CLLocation alloc] initWithLatitude:secondLatitude longitude:secondLongitude];
        double distToFirst = fabs([lastLocation distanceFromLocation:firstLocation]);
        double distToSecond = fabs([lastLocation distanceFromLocation:secondLocation]);
        
        return distToFirst > distToSecond;
    }];
    response[@"response"] = sortedArray;
    resolve(response);
}

@end

