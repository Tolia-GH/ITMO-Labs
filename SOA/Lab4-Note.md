

1. SOA Lab4 should be developped base on Lab2(Not based on Lab3!)
2. Need 2-3 helios account of student on helios to deploy Lab4
3. For each account deploy 1 instance of Service1 and Service2, make sure different service instance use different resources and different database on helios
4. Change Service1 from Restful to SOAP, Service2 just keep on Restful
5. Use Mule ESB to integrate 2 service together
6. （在Mule上？）实现一个额外的 REST 层，以便在无需修改客户端应用程序服务的情况下对其进行访问。所开发的 REST 层除了 SOAP 服务调用之外，不应包含任何其他逻辑。