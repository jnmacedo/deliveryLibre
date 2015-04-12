//
//  Chat.swift
//  DLibre
//
//  Created by Damian Pintos on 11/4/15.
//  Copyright (c) 2015 Damian Pintos. All rights reserved.
//

import UIKit

class chat: UIViewController {
    
    //outlets
    @IBOutlet weak var chatView: UIWebView!
    
    var producto:String?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
        self.navigationItem.title = "Chats"
        let delegate = UIApplication.sharedApplication().delegate as! AppDelegate
        let requestURL = NSURL(string:"https://deliverylibre.herokuapp.com/chat")
        println(requestURL)
        let request = NSURLRequest(URL: requestURL!)
        
        chatView.loadRequest(request)
        self.navigationItem.title = producto
    
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: - Navigation
    
    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
    // Get the new view controller using segue.destinationViewController.
    // Pass the selected object to the new view controller.
        var secondView = segue.destinationViewController as? chat

    }
    
}

