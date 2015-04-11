//
//  PostServices.swift
//  DLibre
//
//  Created by Damian Pintos on 11/4/15.
//  Copyright (c) 2015 Damian Pintos. All rights reserved.
//

import Foundation

class PostServices{
    var settings:Settings!
    
    init(){
        self.settings = Settings()
    }
    
    
    func addUserPostRequest(url:String,usuario:Session) ->	(){
        let myUrl = NSURL(string: url)
        let request = NSMutableURLRequest(URL: myUrl!)
        request.HTTPMethod = "POST"
        
        let postString = "clientId=\(usuario.id)&token=\(usuario.token)&device=\(usuario.device)"
        
        request.HTTPBody = postString.dataUsingEncoding(NSUTF8StringEncoding)
        
        let task = NSURLSession.sharedSession().dataTaskWithRequest(request) {
            (data,response,error) in
            
            var err: NSError?
            var json = NSJSONSerialization.JSONObjectWithData(data, options: NSJSONReadingOptions.MutableContainers, error: &err) as! NSDictionary
        }
        task.resume()
    }
    
    
}