using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using FirebaseAdmin;
using FirebaseAdmin.Messaging;
using Google.Apis.Auth.OAuth2;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using webAPI.Models;
using webAPI.NET.Data;
using webAPI.NET.Models;
using webAPI.NET.Services;
using Message = webAPI.Models.Message;

namespace webAPI.NET.Controllers
{
    [Route("api/transfer")]
    [ApiController]
    public class TransfersController : ControllerBase
    {
        private readonly IMessageService _service;

        public TransfersController(IMessageService service)
        {
            _service = service;
        }


        // POST: api/transfer
        [HttpPost]
        public async Task<ActionResult<Transfer>> PostTransfer([FromBody] Transfer transfer)
        {
            Message message = new Message(transfer.Content, DateTime.Now, false);
            var isTransfer = await _service.Post(transfer.To, transfer.From, message);
            if(!isTransfer)
            {
                return BadRequest();
            }
            Task.Run( () => {
                Notify(transfer);
            });
            return StatusCode(201);
        }

        private void Notify(Transfer transfer)
        {
            if (FirebaseApp.DefaultInstance == null)
            {
                FirebaseApp.Create(new AppOptions
                {
                    Credential = GoogleCredential.FromFile("key.json")
                });
            }
            if (!FirebaseTokenController.tokens.ContainsKey(transfer.To))
            {
                return;
            }
            string token = FirebaseTokenController.tokens[transfer.To];
            var msg = new FirebaseAdmin.Messaging.Message()
            {
                Data = new Dictionary<string, string>()
                {
                    { "Sent By", transfer.From },
                    { "Message", transfer.Content }
                },
                Token = token,
                Notification = new Notification()
                {
                    Title = "New Message",
                    Body = transfer.Content
                }
            };
            FirebaseMessaging.DefaultInstance.SendAsync(msg);
        }
    }
}
