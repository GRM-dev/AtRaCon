using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AtRaCon_Client.Core.Connection
{
    public class StreamChannelHandler
    {
        public static IChannel GetNewChannel()
        {
            IChannel channel=new StreamChannel();

            return channel;
        }
    }
}
