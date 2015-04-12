//
//  Ventas.swift
//  DLibre
//
//  Created by Damian Pintos on 11/4/15.
//  Copyright (c) 2015 Damian Pintos. All rights reserved.
//

import UIKit

class ventas: UIViewController, UITableViewDataSource, UITableViewDelegate {
    
    @IBOutlet weak var ventas: UITableView!
    
    var service:GetServices!
    var post:PostServices!
    var conversaciones = [Conversaciones]()
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        
        // Do any additional setup after loading the view.
        self.ventas.registerClass(UITableViewCell.self, forCellReuseIdentifier: "cell")
        self.ventas.dataSource = self
        self.ventas.delegate = self
        let Delegate = UIApplication.sharedApplication().delegate as! AppDelegate
        
        service = GetServices()
        service.Get(Delegate.token!){
            (reponse) in
            self.loadRecents(reponse["list"] as! NSArray)
            dispatch_async(dispatch_get_main_queue()){
                self.ventas.reloadData()
            }
            
        }
    }
    
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        var cell = tableView.dequeueReusableCellWithIdentifier("cell", forIndexPath: indexPath)as! UITableViewCell
        let conversacion = conversaciones[indexPath.row]
        cell.textLabel?.text = conversacion.buyer
        cell.detailTextLabel?.text = "asdasd"
        cell.accessoryType = UITableViewCellAccessoryType.DisclosureIndicator
        return cell
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return conversaciones.count
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        println(indexPath)
        self.performSegueWithIdentifier("ventasChat", sender: self)
    }
    
    
    func tableView(tableView: UITableView, editActionsForRowAtIndexPath indexPath: NSIndexPath) -> [AnyObject]? {
        return nil
    }
    
    func loadRecents(recents:NSArray) -> (){
        var buyer:String!
        var seller:String!
        var producto:String!
        for item in recents{
            buyer = item["nickname_seller"] as! String
            seller = item["nickname_buyer"] as! String
            producto = item["product_name"] as! String
            let delegate = UIApplication.sharedApplication().delegate as! AppDelegate
            delegate.idCompra = item["id_compra"] as? Int
            delegate.usuario = seller as? String
            var conversacion = Conversaciones(buyer: buyer, seller: seller,producto: producto)
            self.conversaciones.append(conversacion)
        }
    }

    
    // MARK: - Navigation
    
    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        var secondView = segue.destinationViewController as? chat
        if let indexPath = self.ventas.indexPathForSelectedRow(){
            var conv:Conversaciones =  conversaciones[indexPath.row]
            secondView?.producto = conv.producto
        }
    }
    
    
    
    /*
    // MARK: - Navigation
    
    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
    // Get the new view controller using segue.destinationViewController.
    // Pass the selected object to the new view controller.
    }
    */
    
}

