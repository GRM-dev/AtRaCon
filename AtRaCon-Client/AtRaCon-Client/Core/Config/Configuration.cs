using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Helios.Util;

namespace AtRaCon_Client.Core.Config
{
    public class Configuration
    {
        private static Configuration _instance;
        private Dictionary<ConfigKey, Config> _config;

        static Configuration()
        {
            _instance = new Configuration();
        }

        private Configuration()
        {
            _config = new Dictionary<ConfigKey, Config>();
        }

        public int GetIntValue(ConfigKey key)
        {
            if (_config.ContainsKey(key))
            {
                var conf = _config[key];
                if (conf.VType == ValueType.INT)
                {
                    return conf.IValue;
                }
                else
                {
                    throw new Exception("Tried to get bad type of Value");
                }
            }
            else
            {
                throw new NullReferenceException("There is no config with key=\"" + key.ToString());
            }
        }
    }
}
