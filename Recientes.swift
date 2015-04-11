//
//  Recientes.swift
//  DLibre
//
//  Created by Damian Pintos on 11/4/15.
//  Copyright (c) 2015 Damian Pintos. All rights reserved.
//

import UIKit

class Recientes: UIViewController,UITableViewDataSource,UITableViewDelegate {

    
    //Outlets
    @IBOutlet weak var recientes: UITableView!
    
    //Variables
    var service:GetServices!
    var post:PostServices!
    var conversaciones = [Conversaciones]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false
        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        self.navigationController?.navigationBarHidden = false
        self.navigationController?.navigationBar.barTintColor = UIColor.yellowColor()
        tabBarController?.tabBar.barTintColor = UIColor.yellowColor()
        self.navigationItem.title = "Recientes"
        
        service = GetServices()
        
        self.recientes.dataSource = self
        self.recientes.delegate = self
        self.recientes.registerClass(UITableViewCell.self, forCellReuseIdentifier: "cell")
        /*
        service.Get(service.settings.convRecent){
            (reponse) in
            self.loadRecents(reponse["list"] as! NSArray)
            dispatch_async(dispatch_get_main_queue()){
                self.recientes.reloadData()
            }
            
        }
        */
        
    }
    
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        // #warning Potentially incomplete method implementation.
        // Return the number of sections.
        return 1
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete method implementation.
        // Return the number of rows in the section.
        //return conversaciones.count
        return 5
    }
    
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        var cell = tableView.dequeueReusableCellWithIdentifier("cell", forIndexPath: indexPath) as! UITableViewCell
       
        cell.accessoryType = UITableViewCellAccessoryType.DisclosureIndicator
        return cell
    }
    
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        println(indexPath)
        self.performSegueWithIdentifier("recientesChat", sender: self)
    }
    
    
    func tableView(tableView: UITableView, editActionsForRowAtIndexPath indexPath: NSIndexPath) -> [AnyObject]? {
        return nil
    }
    
    func loadRecents(recents:NSArray) -> (){
        var buyer:String!
        var seller:String!
        for item in recents{
            buyer = item["buyer"] as! String
            seller = item["seller"] as! String
            var conversacion = Conversaciones(buyer: buyer, seller: seller)
            self.conversaciones.append(conversacion)
        }
        
    }

    
}
