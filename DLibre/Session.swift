//
//  Session.swift
//  DLibre
//
//  Created by Damian Pintos on 11/4/15.
//  Copyright (c) 2015 Damian Pintos. All rights reserved.
//

import Foundation

class Session{
    
    var id:Int
    var token:String
    var device:String
    
    
    init(id:Int,token:String, device: String){
        self.id = id
        self.token = token
        self.device = device
    }
    
    func toJson() -> String{
        return ""
    }
}
