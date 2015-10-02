using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography.X509Certificates;
using System.Text;
using System.Threading.Tasks;

namespace AtRaCon_Client.Core.Connection
{
    public interface IChannel
    {
        void WriteMsg(string msg);
        void Write(object obj);
        string ReceiveMsg();
        object Receive();
        void Connect();
        void Disconnect();

    }
}
