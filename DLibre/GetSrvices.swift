//
//  File.swift
//  DLibre
//
//  Created by Damian Pintos on 11/4/15.
//  Copyright (c) 2015 Damian Pintos. All rights reserved.
//

import Foundation

class GetServices{
    var settings:Settings!
    
    init(){
        self.settings = Settings()
    }
    
    func Get(url:String, callback:(NSDictionary) ->()) {
        request(url, callback: callback)
    }
    
    func request(url:String, callback:(NSDictionary) ->	()){
        var test = NSURL(string: url)
        let task = NSURLSession.sharedSession().dataTaskWithURL(test!){
            
            (data, response, error) in
            
            var error:NSError?
            var response = NSJSONSerialization.JSONObjectWithData(data, options: NSJSONReadingOptions.MutableContainers, error: &error) as! NSDictionary
            callback(response)
        }
        println(task.description)
        task.resume()
        
    }
    
    
}