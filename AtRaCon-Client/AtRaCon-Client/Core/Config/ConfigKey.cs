using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AtRaCon_Client.Core.Config
{
    public class ConfigKey
    {
        public static readonly Config HostAddress = new Config("HOST_ADDRESS","127.0.0.1");
        public static readonly Config HostPort = new Config("HOST_PORT",22520);
        public static readonly Config ConnectionTimeout = new Config("CON_TIMEOUT",10);
        public static readonly Config AuthLogin = new Config("AUTH_LOGIN",true);
        
    }
}
