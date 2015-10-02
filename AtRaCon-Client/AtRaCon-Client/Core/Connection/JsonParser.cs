using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace AtRaCon_Client.Core.Connection
{
    public static class JsonParser
    {
        public static string ToString(object obj)
        {
            var type = obj.GetType();
            var objS = JsonConvert.SerializeObject(obj);
            var output = JSonObject.Wrap(objS,type);
            return output;
        }

        public static object ToObject(string msg)
        {
            var wObj=(JSonObject)JsonConvert.DeserializeObject(msg);
            var obj=JSonObject.Unwrap(wObj);
            return obj;
        }

        class JSonObject
        {
            private readonly string _obj;
            private readonly Type _objType;

            private JSonObject(Type objType,string obj)
            {
                this._objType = objType;
                this._obj = obj;
            }

             string GetObj()
            {
                return _obj;
            }

             Type GetType()
            {
                return _objType;
            }

            public static string Wrap(string obj, Type type)
            {
                var jObj=new JSonObject(type,obj);
                var wrappedObj = JsonConvert.SerializeObject(jObj);
                return wrappedObj;
            }

            public static object Unwrap(JSonObject obj)
            {
                return obj.GetObj();
            }

            public static Type GetType(JSonObject obj)
            {
                return obj.GetType();
            }
        }
    }
}
