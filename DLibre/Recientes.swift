//
//  Recientes.swift
//  DLibre
//
//  Created by Damian Pintos on 11/4/15.
//  Copyright (c) 2015 Damian Pintos. All rights reserved.
//

import Foundation

class recientes: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        //button.hidden = true
        view.backgroundColor = UIColor.yellowColor()
        navigationController?.navigationBar.backgroundColor = UIColor.yellowColor()
        self.webView.delegate = self
        
        navigationController?.navigationBarHidden = true
        
        service = GetServices()
        service.Get(service.settings.inicio){
            (reponse) in
            self.url = self.getURL(reponse["url"]! as String)
            self.loadUrl()
        }
        
    }

}