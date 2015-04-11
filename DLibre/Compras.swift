//
//  Compras.swift
//  DLibre
//
//  Created by Damian Pintos on 11/4/15.
//  Copyright (c) 2015 Damian Pintos. All rights reserved.
//

import UIKit

class compras: UIViewController,UITableViewDelegate, UITableViewDataSource {
    
    var service:GetServices!
    var post:PostServices!
    var conversaciones = [Conversaciones]()
    
    @IBOutlet weak var compras: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loadingd the view.
        self.compras.registerClass(UITableViewCell.self, forCellReuseIdentifier: "cell")
        self.compras.dataSource = self
        self.compras.delegate = self
        
        service = GetServices()
        
        /*
        service.Get(service.settings.convSeller){
            (reponse) in
            self.loadRecents(reponse["list"] as! NSArray)
            dispatch_async(dispatch_get_main_queue()){
                self.compras.reloadData()
            }
            
        }
*/
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        var cell = tableView.dequeueReusableCellWithIdentifier("cell", forIndexPath: indexPath)as! UITableViewCell
        let conversacion = conversaciones[indexPath.row]
        //cell.textLabel?.text = conversacion.buyer
        cell.accessoryType = UITableViewCellAccessoryType.DisclosureIndicator
        return cell
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return conversaciones.count
    }
    
    func tableView(tableView: UITableView, willSelectRowAtIndexPath indexPath: NSIndexPath) -> NSIndexPath? {
        return indexPath
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
        
        if segue.identifier == "Chats"
        {
        segue.destinationViewController as! chat
        }
    }
    
    func loadRecents(recents:NSArray) -> (){
        var buyer:String!
        var seller:String!
        for String in recents{
            // buyer = String["buyer"] as NSString
            // seller = String["seller"] as NSString
            var conversacion = Conversaciones(buyer: buyer, seller: seller)
            self.conversaciones.append(conversacion)
        }
        
    }
    
    
}
