//
//  Compras.swift
//  DLibre
//
//  Created by Damian Pintos on 11/4/15.
//  Copyright (c) 2015 Damian Pintos. All rights reserved.
//

import UIKit

class Compra: UIViewController,UITableViewDelegate, UITableViewDataSource {
    
//outlets
    @IBOutlet weak var compras: UITableView!
    
    //Variables
    var service:GetServices!
    var post:PostServices!
    var conversaciones = [Conversaciones]()

    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
        
        self.navigationController?.navigationBarHidden = false
        self.navigationController?.navigationBar.barTintColor = UIColor.yellowColor()
        tabBarController?.tabBar.barTintColor = UIColor.yellowColor()
        self.navigationItem.title = "Recientes"
        self.compras.registerClass(UITableViewCell.self, forCellReuseIdentifier: "cell")
        self.compras.dataSource = self
        self.compras.delegate = self
        let Delegate = UIApplication.sharedApplication().delegate as! AppDelegate
        println(Delegate.token)
        
        service = GetServices()
        service.Get(Delegate.token!){
            (reponse) in
            self.loadRecents(reponse["list"] as! NSArray)
            dispatch_async(dispatch_get_main_queue()){
                self.compras.reloadData()
            }
            
        }

    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        var cell = tableView.dequeueReusableCellWithIdentifier("cell", forIndexPath: indexPath)as! UITableViewCell
        //cell = UITableViewCell(style: UITableViewCellStyle.Subtitle, reuseIdentifier: "cell")
        let conversacion = conversaciones[indexPath.row]
        cell.textLabel?.text = conversacion.seller
        cell.accessoryType = UITableViewCellAccessoryType.DisclosureIndicator
        return cell
    }
    
    
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return conversaciones.count
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        println(indexPath)
        self.performSegueWithIdentifier("comprasChat", sender: self)
    }
    
    
    func tableView(tableView: UITableView, editActionsForRowAtIndexPath indexPath: NSIndexPath) -> [AnyObject]? {
        return nil
    }
    
    
    // MARK: - Navigation
    
    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        var secondView = segue.destinationViewController as? chat
        if let indexPath = self.compras.indexPathForSelectedRow(){
            var conv:Conversaciones =  conversaciones[indexPath.row]
            secondView?.producto = conv.producto
        }
      
    }
    
    func loadRecents(recents:NSArray) -> (){
        var buyer:String!
        var seller:String!
        var producto:String!
        for item in recents{
            
            producto = item["product_name"] as! String
            buyer = item["nickname_seller"] as! String
            seller = item["nickname_buyer"] as! String
            var conversacion = Conversaciones(buyer: buyer, seller: seller,producto: producto)
            self.conversaciones.append(conversacion)
        }
    }

    
    
    // MARK: - Navigation
    
    // In a storyboard-based application, you will often want to do a little preparation before navigation
    
    
}
