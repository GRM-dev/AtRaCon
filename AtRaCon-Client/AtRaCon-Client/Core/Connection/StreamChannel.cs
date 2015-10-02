using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AtRaCon_Client.Core.Connection
{
    class StreamChannel : IChannel
    {
        public void WriteMsg(string msg)
        {
            throw new NotImplementedException();
        }

        public void Write(object obj)
        {
            throw new NotImplementedException();
        }

        public string ReceiveMsg()
        {
            throw new NotImplementedException();
        }

        public object Receive()
        {
            throw new NotImplementedException();
        }

        public void Connect()
        {
            throw new NotImplementedException();
        }

        public void Disconnect()
        {
            throw new NotImplementedException();
        }
    }
}
