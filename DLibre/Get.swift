//
//  Get.swift
//  DLibre
//
//  Created by Damian Pintos on 11/4/15.
//  Copyright (c) 2015 Damian Pintos. All rights reserved.
//

import Foundation

class Get{
    
    var id:String
    var nombre:String
    
    
    init(id:String,nombre:String){
        self.id = id
        self.nombre = nombre
    }
    
    func toJson() -> String{
        return ""
    }
}