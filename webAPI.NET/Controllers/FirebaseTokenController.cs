
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using NuGet.Common;
using System.Security.Claims;
using webAPI.NET.Models;

namespace webAPI.NET.Controllers
{
    [Authorize]
    [Route("api/[controller]")]
    [ApiController]
    public class FirebaseTokenController : ControllerBase
    {
        public static Dictionary<string, string> tokens = new Dictionary<string, string>();



        /* function return the userId based on the JWT token */
        private string GetUserIdFromToken()
        {
            var identity = HttpContext.User.Identity as ClaimsIdentity;
            var userId = identity.FindFirst("UserId").Value;
            return userId;
        }

        // POST: api/users/login
        [Route("/api/users/token")]
        [HttpPost]
        public void SetToken([FromBody] TokenRequest token)
        {
            string userid = GetUserIdFromToken();
            tokens[userid] = token.TokenId;
        }
    }

}
