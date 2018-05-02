package com.exam.portal.security;

public class AuthenticationFilter /*extends OncePerRequestFilter*/ {

	/*private LoginService loginService;

	public AuthenticationFilter(LoginService loginService) {
		super();
		this.loginService = loginService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String xAuth = request.getHeader("authToken");
		Authentication auth = null;
		try {
			System.out.println("xAuth token === " + xAuth);
			if (!isValid(xAuth)) {
				throw new SecurityException("Unauthorized");
			}
			UserInfo appUser = loginService.getUser(xAuth);
			if (appUser == null) {
				throw new UsernameNotFoundException("Please login to continue");
			}

			if (appUser.getRole() != null && !appUser.getRole().name().isEmpty()) {
				Function<UserRole, SimpleGrantedAuthority> mapper = a -> {
					return new SimpleGrantedAuthority(a.name());
				};
				auth = new DemoAuthenticationToken(Arrays.asList(appUser.getRole()).stream().map(mapper).collect(Collectors.toSet()),
						appUser, appUser.getUserInfoID());
			} else {
				auth = new AnonymousAuthenticationToken(xAuth, xAuth,
						Arrays.asList(new SimpleGrantedAuthority("NO_ROLE")));
			}
			SecurityContextHolder.getContext().setAuthentication(auth);
			filterChain.doFilter(request, response);
		} catch (RuntimeException e) {
			System.out.println("Entered into catch Runtime Exception");
			e.printStackTrace();
			ReturnHolder customResponse = new ReturnHolder(false, new ErrorObject("ERR401", e.getMessage()));
			response.setContentType("application/json; charset=utf-8");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write(new ObjectMapper().writeValueAsString(customResponse));
		} catch (Exception e) {
			e.printStackTrace();
			ReturnHolder customResponse = new ReturnHolder(false, new ErrorObject("ERR500", "Something went wrong"));
			response.setContentType("application/json; charset=utf-8");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write(new ObjectMapper().writeValueAsString(customResponse));
		}

	}

	private boolean isValid(String authToken) {
		if (authToken != null && authToken != "")
			return true;
		else
			return false;
	}*/

}
