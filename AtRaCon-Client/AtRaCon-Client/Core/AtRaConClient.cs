using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using AtRaCon_Client.Core.Connection;

namespace AtRaCon_Client.Core
{
    public class AtRaConClient
    {
        private MainWindow mainWindow;
        private IChannel channel;

        public AtRaConClient(MainWindow mainWindow)
        {
            this.mainWindow = mainWindow;
        }

        public void Connect()
        {
             channel = StreamChannelHandler.GetNewChannel();
             channel.Connect();
        }
    }
}
