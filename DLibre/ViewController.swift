//
//  ViewController.swift
//  DLibre
//
//  Created by Damian Pintos on 11/4/15.
//  Copyright (c) 2015 Damian Pintos. All rights reserved.
//

import UIKit

class ViewController: UIViewController,UIWebViewDelegate {

    //Outlets
    @IBOutlet weak var webView: UIWebView!
    @IBOutlet weak var button: UIButton!
    
    //Variables
    var getCollection = [Get]()
    var service:GetServices!
    var post:PostServices!
    var session:Session!
    var url:String?
   
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        button.hidden = true
        view.backgroundColor = UIColor.yellowColor()
        navigationController?.navigationBar.backgroundColor = UIColor.yellowColor()
        self.webView.delegate = self
        
        navigationController?.navigationBarHidden = true
        
        service = GetServices()
        service.Get(service.settings.inicio){
            (reponse) in
            self.url = self.getURL(reponse["url"]! as! String)
            self.loadUrl()
        }
    
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    //Obtengo url de logueo
    func getURL(url:String) -> String{
        var url = url as NSString
        return url as String
    }
    
    //carga url en el webView
    func loadUrl()
    {
        let requestURL = NSURL(string:self.url!)
        let request = NSURLRequest(URL: requestURL!)
        webView.loadRequest(request)
        
    }
    
    //Recuperan datos del pedido
    func getToken(token:String) -> String{
        return token
    }
    
    func getId(id:Int) -> Int{
        return id
    }

    func webView(webView: UIWebView, shouldStartLoadWithRequest request: NSURLRequest, navigationType: UIWebViewNavigationType) -> Bool {
        
        var test = "https://holaperros2.herokuapp.com/code"
        var test2 = request.URL!.absoluteString?.componentsSeparatedByString("?")
        
        if test == test2?.first
        {
            service.Get(request.URL!.absoluteString!){
                (reponse) in
                var token = reponse["access_token"]! as! String
                var id = reponse["user_id"]! as! Int
                var device = UIDevice.currentDevice().identifierForVendor.UUIDString
                println(request.URL?.absoluteString)
                self.session = Session(id: id,token: token, device: device)
                let delegate = UIApplication.sharedApplication().delegate as! AppDelegate
                delegate.token = self.service.settings.convBuyer + "?token=\(token)"
                self.post = PostServices()
                self.post.addUserPostRequest(self.service.settings.addUser, usuario: self.session)
            }
            webView.removeFromSuperview()
            button.hidden = false
            return false
        }
        else
        {
            return true
        }
        
        
    }
}

